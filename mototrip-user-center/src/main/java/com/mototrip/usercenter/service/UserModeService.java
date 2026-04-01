package com.mototrip.usercenter.service;

import com.mototrip.usercenter.dto.request.UpdateUserModeRequest;
import com.mototrip.usercenter.dto.response.UserModeResponse;

public interface UserModeService {
    UserModeResponse getUserMode(Long userId);
    UserModeResponse updateUserMode(Long userId, UpdateUserModeRequest request);
    UserModeResponse switchMode(Long userId, String newMode);
}
