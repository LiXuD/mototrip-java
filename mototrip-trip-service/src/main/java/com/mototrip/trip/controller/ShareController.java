package com.mototrip.trip.controller;

import com.mototrip.common.context.UserContext;
import com.mototrip.common.response.PageResult;
import com.mototrip.common.response.Result;
import com.mototrip.trip.entity.TripShare;
import com.mototrip.trip.service.ShareService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "行程分享", description = "行程分享管理")
@RestController
@RequestMapping("/shares")
@RequiredArgsConstructor
public class ShareController {
    private final ShareService shareService;

    @Operation(summary = "创建行程分享")
    @PostMapping
    public Result<TripShare> create(@RequestParam Long tripId) {
        return Result.success(shareService.create(UserContext.getUserId(), tripId));
    }

    @Operation(summary = "获取分享详情")
    @GetMapping("/{id}")
    public Result<TripShare> findById(@PathVariable Long id) {
        return Result.success(shareService.findById(id));
    }

    @Operation(summary = "获取我的分享列表")
    @GetMapping("/my")
    public Result<PageResult<TripShare>> findMyShares(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return Result.success(shareService.findMyShares(UserContext.getUserId(), page, pageSize));
    }

    @Operation(summary = "标记为已分享")
    @PostMapping("/{id}/mark-shared")
    public Result<Void> markShared(@PathVariable Long id) {
        shareService.markShared(UserContext.getUserId(), id);
        return Result.success();
    }

    @Operation(summary = "删除行程分享")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        shareService.delete(UserContext.getUserId(), id);
        return Result.success();
    }
}
