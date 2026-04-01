package com.mototrip.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mototrip.usercenter.dto.request.UpdateProfileRequest;
import com.mototrip.usercenter.dto.response.PublicUserResponse;
import com.mototrip.usercenter.entity.User;
import com.mototrip.usercenter.mapper.UserMapper;
import com.mototrip.usercenter.service.UserService;
import com.mototrip.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public PublicUserResponse findById(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new NotFoundException("用户不存在");
        }
        return toPublicUserResponse(user);
    }

    @Override
    public PublicUserResponse updateProfile(Long userId, UpdateProfileRequest request) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new NotFoundException("用户不存在");
        }
        if (request.getNickname() != null) user.setNickname(request.getNickname());
        if (request.getAvatar() != null) user.setAvatar(request.getAvatar());
        if (request.getBio() != null) user.setBio(request.getBio());
        if (request.getMotorcycle() != null) user.setMotorcycle(request.getMotorcycle());
        if (request.getRidingExperience() != null) user.setRidingExperience(request.getRidingExperience());
        if (request.getMotorcycleType() != null) user.setMotorcycleType(request.getMotorcycleType());
        userMapper.updateById(user);
        return toPublicUserResponse(user);
    }

    @Override
    public List<PublicUserResponse> findByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        List<User> users = userMapper.selectList(
                new LambdaQueryWrapper<User>().in(User::getId, ids)
        );
        return users.stream().map(this::toPublicUserResponse).collect(Collectors.toList());
    }

    private PublicUserResponse toPublicUserResponse(User user) {
        PublicUserResponse response = new PublicUserResponse();
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
