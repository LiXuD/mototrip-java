package com.mototrip.mapsafety.controller;

import com.mototrip.common.context.UserContext;
import com.mototrip.common.response.PageResult;
import com.mototrip.common.response.Result;
import com.mototrip.mapsafety.dto.request.CreateOfflineMapRequest;
import com.mototrip.mapsafety.entity.OfflineMap;
import com.mototrip.mapsafety.entity.OfflineMapTile;
import com.mototrip.mapsafety.service.OfflineMapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Tag(name = "离线地图", description = "离线地图管理")
@RestController
@RequestMapping("/api/maps")
@RequiredArgsConstructor
public class OfflineMapController {
    private final OfflineMapService offlineMapService;

    @Operation(summary = "获取离线地图列表")
    @GetMapping
    public Result<PageResult<OfflineMap>> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return Result.success(offlineMapService.findAll(page, pageSize));
    }

    @Operation(summary = "获取离线地图详情")
    @GetMapping("/{id}")
    public Result<OfflineMap> findById(@PathVariable Long id) {
        return Result.success(offlineMapService.findById(id));
    }

    @Operation(summary = "创建离线地图")
    @PostMapping
    public Result<OfflineMap> create(@Valid @RequestBody CreateOfflineMapRequest request) {
        return Result.success(offlineMapService.create(UserContext.getUserId(), request));
    }

    @Operation(summary = "删除离线地图")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        offlineMapService.delete(UserContext.getUserId(), id);
        return Result.success();
    }

    @Operation(summary = "生成地图瓦片")
    @GetMapping("/{id}/tiles")
    public Result<List<Map<String, Object>>> generateTiles(@PathVariable Long id) {
        return Result.success(offlineMapService.generateTiles(id));
    }

    @Operation(summary = "获取地图瓦片")
    @GetMapping("/{id}/tile/{z}/{x}/{y}")
    public Result<OfflineMapTile> getTile(@PathVariable Long id, @PathVariable int z, @PathVariable int x, @PathVariable int y) {
        return Result.success(offlineMapService.getTile(id, z, x, y));
    }

    @Operation(summary = "开始下载离线地图")
    @PostMapping("/{id}/download")
    public Result<Void> startDownload(@PathVariable Long id) {
        offlineMapService.startDownload(id);
        return Result.success();
    }
}
