package com.mototrip.mapsafety.controller;

import com.mototrip.common.context.UserContext;
import com.mototrip.common.response.PageResult;
import com.mototrip.common.response.Result;
import com.mototrip.mapsafety.dto.request.CreateNoParkingZoneRequest;
import com.mototrip.mapsafety.dto.request.UpdateNoParkingZoneRequest;
import com.mototrip.mapsafety.entity.NoParkingZone;
import com.mototrip.mapsafety.service.NoParkingZoneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "禁停区域", description = "禁停区域管理")
@RestController
@RequestMapping("/no-parking-zones")
@RequiredArgsConstructor
public class NoParkingZoneController {
    private final NoParkingZoneService noParkingZoneService;

    @Operation(summary = "获取禁停区域列表")
    @GetMapping
    public Result<PageResult<NoParkingZone>> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return Result.success(noParkingZoneService.findAll(page, pageSize));
    }

    @Operation(summary = "获取禁停区域详情")
    @GetMapping("/{id}")
    public Result<NoParkingZone> findById(@PathVariable Long id) {
        return Result.success(noParkingZoneService.findById(id));
    }

    @Operation(summary = "查询附近禁停区域")
    @GetMapping("/nearby")
    public Result<List<NoParkingZone>> findNearby(
            @RequestParam double lat, @RequestParam double lng,
            @RequestParam(defaultValue = "5000") double radius) {
        return Result.success(noParkingZoneService.findNearby(lat, lng, radius));
    }

    @Operation(summary = "创建禁停区域")
    @PostMapping
    public Result<NoParkingZone> create(@Valid @RequestBody CreateNoParkingZoneRequest request) {
        return Result.success(noParkingZoneService.create(UserContext.getUserId(), request));
    }

    @Operation(summary = "更新禁停区域")
    @PutMapping("/{id}")
    public Result<NoParkingZone> update(@PathVariable Long id, @Valid @RequestBody UpdateNoParkingZoneRequest request) {
        return Result.success(noParkingZoneService.update(UserContext.getUserId(), id, request));
    }

    @Operation(summary = "删除禁停区域")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        noParkingZoneService.delete(UserContext.getUserId(), id);
        return Result.success();
    }
}
