package com.mototrip.mapsafety.controller;

import com.mototrip.common.context.UserContext;
import com.mototrip.common.response.PageResult;
import com.mototrip.common.response.Result;
import com.mototrip.mapsafety.dto.request.CreateLocationShareRequest;
import com.mototrip.mapsafety.dto.request.UpdateLocationRequest;
import com.mototrip.mapsafety.entity.LocationShare;
import com.mototrip.mapsafety.entity.LocationUpdate;
import com.mototrip.mapsafety.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "位置共享", description = "实时位置共享")
@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @Operation(summary = "获取共享给我的位置")
    @GetMapping("/shared")
    public Result<PageResult<LocationShare>> findSharedWithMe(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return Result.success(locationService.findSharedWithMe(UserContext.getUserId(), page, pageSize));
    }

    @Operation(summary = "获取我共享的位置列表")
    @GetMapping("/shares")
    public Result<PageResult<LocationShare>> findMyShares(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return Result.success(locationService.findMyShares(UserContext.getUserId(), page, pageSize));
    }

    @Operation(summary = "获取位置共享详情")
    @GetMapping("/{id}")
    public Result<LocationShare> findById(@PathVariable Long id) {
        return Result.success(locationService.findById(id));
    }

    @Operation(summary = "获取位置历史记录")
    @GetMapping("/{id}/history")
    public Result<PageResult<LocationUpdate>> getHistory(
            @PathVariable Long id,
            @RequestParam(defaultValue = "24") int hours) {
        return Result.success(locationService.getHistory(id, hours));
    }

    @Operation(summary = "创建位置共享")
    @PostMapping("/share")
    public Result<LocationShare> createShare(@Valid @RequestBody CreateLocationShareRequest request) {
        return Result.success(locationService.createShare(UserContext.getUserId(), request));
    }

    @Operation(summary = "更新位置")
    @PostMapping("/{id}/update")
    public Result<Void> updateLocation(@PathVariable Long id, @Valid @RequestBody UpdateLocationRequest request) {
        locationService.updateLocation(UserContext.getUserId(), id, request);
        return Result.success();
    }

    @Operation(summary = "停止位置共享")
    @PostMapping("/{id}/stop")
    public Result<Void> stopSharing(@PathVariable Long id) {
        locationService.stopSharing(UserContext.getUserId(), id);
        return Result.success();
    }

    @Operation(summary = "删除位置共享")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        locationService.delete(UserContext.getUserId(), id);
        return Result.success();
    }
}
