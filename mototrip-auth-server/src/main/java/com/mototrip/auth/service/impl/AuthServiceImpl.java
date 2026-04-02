package com.mototrip.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mototrip.auth.dto.request.LoginRequest;
import com.mototrip.auth.dto.request.RegisterRequest;
import com.mototrip.auth.dto.request.WechatLoginRequest;
import com.mototrip.auth.dto.response.LoginResponse;
import com.mototrip.auth.dto.response.UserResponse;
import com.mototrip.auth.entity.User;
import com.mototrip.auth.mapper.UserMapper;
import com.mototrip.auth.service.AuthService;
import com.mototrip.common.exception.BadRequestException;
import com.mototrip.common.exception.ConflictException;
import com.mototrip.common.exception.UnauthorizedException;
import com.mototrip.common.util.JwtUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    @Value("${jwt.expiration:604800}")
    private Long jwtExpiration;

    @Value("${wechat.appid:}")
    private String wechatAppid;

    @Value("${wechat.secret:}")
    private String wechatSecret;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    @Override
    public void register(RegisterRequest request) {
        // 检查用户名是否已存在
        Long count = userMapper.selectCount(
                new LambdaQueryWrapper<User>().eq(User::getUsername, request.getUsername())
        );
        if (count > 0) {
            throw new ConflictException("用户名已存在");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setNickname(request.getNickname());
        user.setFollowersCount(0);
        user.setFollowingCount(0);
        user.setRidingExperience("newbie");
        user.setMotorcycleType("scooter");
        userMapper.insert(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userMapper.selectOne(
                new LambdaQueryWrapper<User>().eq(User::getUsername, request.getUsername())
        );
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("用户名或密码错误");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        redisTemplate.opsForValue().set(
                "refresh_token:" + user.getId(),
                token,
                jwtExpiration,
                TimeUnit.SECONDS
        );

        return LoginResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresIn(jwtExpiration)
                .build();
    }

    @Override
    public LoginResponse wechatLogin(WechatLoginRequest request) {
        // 调用微信 API 获取 openid
        String url = String.format(
                "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                wechatAppid, wechatSecret, request.getCode()
        );

        RestTemplate restTemplate = new RestTemplate();
        String response;
        try {
            response = restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            log.error("微信登录API调用失败", e);
            throw new BadRequestException("微信登录失败");
        }

        String openid;
        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            openid = jsonNode.get("openid").asText();
        } catch (IOException e) {
            log.error("解析微信响应失败: {}", response, e);
            throw new BadRequestException("微信登录失败");
        }

        // 查找或创建用户
        User user = userMapper.selectOne(
                new LambdaQueryWrapper<User>().eq(User::getOpenid, openid)
        );
        if (user == null) {
            user = new User();
            user.setUsername("wx_" + openid.substring(0, 8));
            user.setNickname("微信用户");
            user.setOpenid(openid);
            user.setPassword(passwordEncoder.encode(openid));
            user.setFollowersCount(0);
            user.setFollowingCount(0);
            user.setRidingExperience("newbie");
            user.setMotorcycleType("scooter");
            userMapper.insert(user);
        }

        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        redisTemplate.opsForValue().set(
                "refresh_token:" + user.getId(),
                token,
                jwtExpiration,
                TimeUnit.SECONDS
        );

        return LoginResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresIn(jwtExpiration)
                .build();
    }

    @Override
    public UserResponse getCurrentUser(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new UnauthorizedException("用户不存在");
        }
        return toUserResponse(user);
    }

    @Override
    public void logout(Long userId) {
        redisTemplate.delete("refresh_token:" + userId);
    }

    private UserResponse toUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setNickname(user.getNickname());
        response.setAvatar(user.getAvatar());
        response.setBio(user.getBio());
        response.setMotorcycle(user.getMotorcycle());
        response.setRidingExperience(user.getRidingExperience());
        response.setMotorcycleType(user.getMotorcycleType());
        response.setFollowersCount(user.getFollowersCount());
        response.setFollowingCount(user.getFollowingCount());
        response.setCreatedAt(user.getCreatedAt());
        return response;
    }
}
