package com.mototrip.social.controller;

import com.mototrip.common.context.UserContext;
import com.mototrip.common.response.PageResult;
import com.mototrip.common.response.Result;
import com.mototrip.social.dto.request.CreateFootprintRequest;
import com.mototrip.social.entity.Footprint;
import com.mototrip.social.entity.FootprintAchievement;
import com.mototrip.social.service.FootprintService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "足迹管理", description = "足迹创建、查询、统计")
@RestController
@RequestMapping("/footprints")
@RequiredArgsConstructor
public class FootprintController {
    private final FootprintService footprintService;

    @Operation(summary = "获取足迹列表")
    @GetMapping
    public Result<PageResult<Footprint>> findAll(
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return Result.success(footprintService.findAll(userId, page, pageSize));
    }

    @Operation(summary = "获取足迹地图数据")
    @GetMapping("/map")
    public Result<List<Map<String, Object>>> findMapData() {
        return Result.success(footprintService.findMapData(UserContext.getUserId()));
    }

    @Operation(summary = "记录足迹")
    @PostMapping
    public Result<Footprint> create(@Valid @RequestBody CreateFootprintRequest request) {
        return Result.success(footprintService.create(UserContext.getUserId(), request));
    }

    @Operation(summary = "删除足迹")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        footprintService.delete(UserContext.getUserId(), id);
        return Result.success();
    }

    @Operation(summary = "获取足迹成就列表")
    @GetMapping("/achievements")
    public Result<List<FootprintAchievement>> getAchievements() {
        return Result.success(footprintService.getAchievements(UserContext.getUserId()));
    }

    @Operation(summary = "获取足迹统计数据")
    @GetMapping("/stats")
    public Result<Map<String, Object>> getStats() {
        return Result.success(footprintService.getStats(UserContext.getUserId()));
    }
}
