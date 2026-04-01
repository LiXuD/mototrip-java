package com.mototrip.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mototrip.usercenter.dto.request.UpdateUserModeRequest;
import com.mototrip.usercenter.dto.response.UserModeResponse;
import com.mototrip.usercenter.entity.UserMode;
import com.mototrip.usercenter.mapper.UserModeMapper;
import com.mototrip.usercenter.service.UserModeService;
import com.mototrip.common.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserModeServiceImpl implements UserModeService {

    private final UserModeMapper userModeMapper;

    @Override
    public UserModeResponse getUserMode(Long userId) {
        UserMode userMode = getOrCreateUserMode(userId);
        return toResponse(userMode);
    }

    @Override
    public UserModeResponse updateUserMode(Long userId, UpdateUserModeRequest request) {
        UserMode userMode = getOrCreateUserMode(userId);
        if (request.getMode() != null) userMode.setMode(request.getMode());
        if (request.getMaxRideDistance() != null) userMode.setMaxRideDistance(request.getMaxRideDistance());
        if (request.getMaxSpeed() != null) userMode.setMaxSpeed(request.getMaxSpeed());
        if (request.getEnableDistanceReminder() != null) userMode.setEnableDistanceReminder(request.getEnableDistanceReminder());
        if (request.getEnableSpeedReminder() != null) userMode.setEnableSpeedReminder(request.getEnableSpeedReminder());
        if (request.getEnableDangerWarning() != null) userMode.setEnableDangerWarning(request.getEnableDangerWarning());
        if (request.getEnableSimplifiedUI() != null) userMode.setEnableSimplifiedUI(request.getEnableSimplifiedUI());
        if (request.getEnableComfortMode() != null) userMode.setEnableComfortMode(request.getEnableComfortMode());
        if (request.getEnableProfessionalFeatures() != null) userMode.setEnableProfessionalFeatures(request.getEnableProfessionalFeatures());
        userModeMapper.updateById(userMode);
        return toResponse(userMode);
    }

    @Override
    public UserModeResponse switchMode(Long userId, String newMode) {
        if (!newMode.matches("newbie|experienced|passenger")) {
            throw new BadRequestException("无效的用户模式");
        }
        UserMode userMode = getOrCreateUserMode(userId);
        userMode.setMode(newMode);
        userModeMapper.updateById(userMode);
        return toResponse(userMode);
    }

    private UserMode getOrCreateUserMode(Long userId) {
        UserMode userMode = userModeMapper.selectOne(
                new LambdaQueryWrapper<UserMode>().eq(UserMode::getUserId, userId)
        );
        if (userMode == null) {
            userMode = new UserMode();
            userMode.setUserId(userId);
            userMode.setMode("newbie");
            userMode.setMaxRideDistance(200);
            userMode.setMaxSpeed(80);
            userMode.setEnableDistanceReminder(true);
            userMode.setEnableSpeedReminder(true);
            userMode.setEnableDangerWarning(true);
            userMode.setEnableSimplifiedUI(false);
            userMode.setEnableComfortMode(false);
            userMode.setEnableProfessionalFeatures(false);
            userModeMapper.insert(userMode);
        }
        return userMode;
    }

    private UserModeResponse toResponse(UserMode userMode) {
        UserModeResponse response = new UserModeResponse();
        response.setId(userMode.getId());
        response.setUserId(userMode.getUserId());
        response.setMode(userMode.getMode());
        response.setMaxRideDistance(userMode.getMaxRideDistance());
        response.setMaxSpeed(userMode.getMaxSpeed());
        response.setEnableDistanceReminder(userMode.getEnableDistanceReminder());
        response.setEnableSpeedReminder(userMode.getEnableSpeedReminder());
        response.setEnableDangerWarning(userMode.getEnableDangerWarning());
        response.setEnableSimplifiedUI(userMode.getEnableSimplifiedUI());
        response.setEnableComfortMode(userMode.getEnableComfortMode());
        response.setEnableProfessionalFeatures(userMode.getEnableProfessionalFeatures());
        response.setCreatedAt(userMode.getCreatedAt());
        response.setUpdatedAt(userMode.getUpdatedAt());
        return response;
    }
}
