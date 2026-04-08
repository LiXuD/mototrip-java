package com.mototrip.auth.controller;

import com.mototrip.auth.dto.request.LoginRequest;
import com.mototrip.auth.dto.request.RegisterRequest;
import com.mototrip.auth.dto.request.WechatLoginRequest;
import com.mototrip.auth.dto.response.LoginResponse;
import com.mototrip.auth.dto.response.UserResponse;
import com.mototrip.auth.service.AuthService;
import com.mototrip.common.context.UserContext;
import com.mototrip.common.response.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "认证管理", description = "用户注册、登录、微信登录")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result<Void> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return Result.success();
    }

    @Operation(summary = "用户名密码登录")
    @PostMapping("/login")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return Result.success(authService.login(request));
    }

    @Operation(summary = "微信小程序登录")
    @PostMapping("/wechat/login")
    public Result<LoginResponse> wechatLogin(@Valid @RequestBody WechatLoginRequest request) {
        return Result.success(authService.wechatLogin(request));
    }

    @Operation(summary = "获取当前用户信息")
    @GetMapping("/current")
    public Result<UserResponse> getCurrentUser() {
        Long userId = UserContext.getUserId();
        return Result.success(authService.getCurrentUser(userId));
    }

    @Operation(summary = "退出登录")
    @PostMapping("/logout")
    public Result<Void> logout() {
        Long userId = UserContext.getUserId();
        authService.logout(userId);
        return Result.success();
    }
}
