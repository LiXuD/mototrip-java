package com.mototrip.auth.service;

import com.mototrip.auth.dto.request.LoginRequest;
import com.mototrip.auth.dto.request.RegisterRequest;
import com.mototrip.auth.dto.request.WechatLoginRequest;
import com.mototrip.auth.dto.response.LoginResponse;
import com.mototrip.auth.dto.response.UserResponse;

public interface AuthService {
    void register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    LoginResponse wechatLogin(WechatLoginRequest request);
    UserResponse getCurrentUser(Long userId);
    void logout(Long userId);
}
