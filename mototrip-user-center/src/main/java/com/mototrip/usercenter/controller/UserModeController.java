package com.mototrip.usercenter.controller;

import com.mototrip.usercenter.dto.request.UpdateUserModeRequest;
import com.mototrip.usercenter.dto.response.UserModeResponse;
import com.mototrip.usercenter.service.UserModeService;
import com.mototrip.common.response.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户模式", description = "用户骑行模式管理")
@RestController
@RequestMapping("/api/user-mode")
@RequiredArgsConstructor
public class UserModeController {

    private final UserModeService userModeService;

    @Operation(summary = "获取当前用户模式")
    @GetMapping
    public Result<UserModeResponse> getUserMode() {
        Long userId = com.mototrip.common.context.UserContext.getUserId();
        return Result.success(userModeService.getUserMode(userId));
    }

    @Operation(summary = "更新用户模式")
    @PutMapping
    public Result<UserModeResponse> updateUserMode(@Valid @RequestBody UpdateUserModeRequest request) {
        Long userId = com.mototrip.common.context.UserContext.getUserId();
        return Result.success(userModeService.updateUserMode(userId, request));
    }

    @Operation(summary = "切换用户模式")
    @PostMapping("/switch")
    public Result<UserModeResponse> switchMode(@RequestParam String mode) {
        Long userId = com.mototrip.common.context.UserContext.getUserId();
        return Result.success(userModeService.switchMode(userId, mode));
    }
}
