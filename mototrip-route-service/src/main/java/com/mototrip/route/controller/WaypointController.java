package com.mototrip.route.controller;

import com.mototrip.common.context.UserContext;
import com.mototrip.common.response.PageResult;
import com.mototrip.common.response.Result;
import com.mototrip.route.dto.request.CreateWaypointRequest;
import com.mototrip.route.dto.request.UpdateWaypointRequest;
import com.mototrip.route.entity.Waypoint;
import com.mototrip.route.service.WaypointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "途经点管理", description = "途经点创建、查询、更新、删除")
@RestController
@RequestMapping("/waypoints")
@RequiredArgsConstructor
public class WaypointController {

    private final WaypointService waypointService;

    @Operation(summary = "获取途经点列表")
    @GetMapping
    public Result<PageResult<Waypoint>> findAll(
            @RequestParam(name = "routeId", required = false) Long routeId,
            @RequestParam(name = "tripId", required = false) Long tripId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return Result.success(waypointService.findAll(routeId, tripId, page, pageSize));
    }

    @Operation(summary = "获取途经点详情")
    @GetMapping("/{id}")
    public Result<Waypoint> findById(@PathVariable("id") Long id) {
        return Result.success(waypointService.findById(id));
    }

    @Operation(summary = "创建途经点")
    @PostMapping
    public Result<Waypoint> create(@Valid @RequestBody CreateWaypointRequest request) {
        Long userId = UserContext.getUserId();
        return Result.success(waypointService.create(userId, request));
    }

    @Operation(summary = "更新途经点")
    @PutMapping("/{id}")
    public Result<Waypoint> update(@PathVariable("id") Long id, @Valid @RequestBody UpdateWaypointRequest request) {
        Long userId = UserContext.getUserId();
        return Result.success(waypointService.update(userId, id, request));
    }

    @Operation(summary = "删除途经点")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable("id") Long id) {
        Long userId = UserContext.getUserId();
        waypointService.delete(userId, id);
        return Result.success();
    }
}
