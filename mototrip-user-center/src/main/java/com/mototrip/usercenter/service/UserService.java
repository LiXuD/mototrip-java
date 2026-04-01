package com.mototrip.usercenter.service;

import com.mototrip.usercenter.dto.request.UpdateProfileRequest;
import com.mototrip.usercenter.dto.response.PublicUserResponse;

import java.util.List;

public interface UserService {
    PublicUserResponse findById(Long id);
    PublicUserResponse updateProfile(Long userId, UpdateProfileRequest request);
    List<PublicUserResponse> findByIds(List<Long> ids);
}
