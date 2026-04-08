package com.mototrip.mapsafety.controller;

import com.mototrip.common.context.UserContext;
import com.mototrip.common.response.PageResult;
import com.mototrip.common.response.Result;
import com.mototrip.mapsafety.dto.request.CreateDangerZoneRequest;
import com.mototrip.mapsafety.dto.request.UpdateDangerZoneRequest;
import com.mototrip.mapsafety.entity.DangerZone;
import com.mototrip.mapsafety.service.DangerZoneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Tag(name = "危险区域", description = "危险区域管理")
@RestController
@RequestMapping("/danger-zones")
@RequiredArgsConstructor
public class DangerZoneController {
    private final DangerZoneService dangerZoneService;

    @Operation(summary = "获取危险区域列表")
    @GetMapping
    public Result<PageResult<DangerZone>> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return Result.success(dangerZoneService.findAll(page, pageSize));
    }

    @Operation(summary = "获取危险区域详情")
    @GetMapping("/{id}")
    public Result<DangerZone> findById(@PathVariable Long id) {
        return Result.success(dangerZoneService.findById(id));
    }

    @Operation(summary = "查询附近危险区域")
    @GetMapping("/nearby")
    public Result<List<DangerZone>> findNearby(
            @RequestParam double lat, @RequestParam double lng,
            @RequestParam(defaultValue = "5000") double radius) {
        return Result.success(dangerZoneService.findNearby(lat, lng, radius));
    }

    @Operation(summary = "创建危险区域")
    @PostMapping
    public Result<DangerZone> create(@Valid @RequestBody CreateDangerZoneRequest request) {
        return Result.success(dangerZoneService.create(UserContext.getUserId(), request));
    }

    @Operation(summary = "更新危险区域")
    @PutMapping("/{id}")
    public Result<DangerZone> update(@PathVariable Long id, @Valid @RequestBody UpdateDangerZoneRequest request) {
        return Result.success(dangerZoneService.update(UserContext.getUserId(), id, request));
    }

    @Operation(summary = "解决危险区域")
    @PostMapping("/{id}/resolve")
    public Result<Void> resolve(@PathVariable Long id) {
        dangerZoneService.resolve(UserContext.getUserId(), id);
        return Result.success();
    }

    @Operation(summary = "忽略危险区域")
    @PostMapping("/{id}/ignore")
    public Result<Void> ignore(@PathVariable Long id) {
        dangerZoneService.ignore(UserContext.getUserId(), id);
        return Result.success();
    }

    @Operation(summary = "删除危险区域")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        dangerZoneService.delete(UserContext.getUserId(), id);
        return Result.success();
    }

    @Operation(summary = "获取危险区域统计")
    @GetMapping("/statistics")
    public Result<Map<String, Object>> getStatistics() {
        return Result.success(dangerZoneService.getStatistics());
    }
}
