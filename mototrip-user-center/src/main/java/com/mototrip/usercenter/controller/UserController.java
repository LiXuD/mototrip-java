package com.mototrip.usercenter.controller;

import com.mototrip.usercenter.dto.request.UpdateProfileRequest;
import com.mototrip.usercenter.dto.response.PublicUserResponse;
import com.mototrip.usercenter.service.UserService;
import com.mototrip.common.response.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "用户管理", description = "用户资料管理")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "获取用户信息")
    @GetMapping("/{id}")
    public Result<PublicUserResponse> getUserById(@PathVariable Long id) {
        return Result.success(userService.findById(id));
    }

    @Operation(summary = "更新用户资料")
    @PutMapping("/profile")
    public Result<PublicUserResponse> updateProfile(@Valid @RequestBody UpdateProfileRequest request) {
        Long userId = com.mototrip.common.context.UserContext.getUserId();
        return Result.success(userService.updateProfile(userId, request));
    }

    @Operation(summary = "批量获取用户信息（内部接口）")
    @GetMapping("/batch")
    public Result<List<PublicUserResponse>> getUsersByIds(@RequestParam List<Long> ids) {
        return Result.success(userService.findByIds(ids));
    }
}
