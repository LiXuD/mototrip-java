package com.mototrip.trip.controller;

import com.mototrip.common.context.UserContext;
import com.mototrip.common.response.PageResult;
import com.mototrip.common.response.Result;
import com.mototrip.trip.dto.request.CreateTripRequest;
import com.mototrip.trip.dto.request.UpdateTripRequest;
import com.mototrip.trip.entity.Trip;
import com.mototrip.trip.service.TripService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "行程管理", description = "骑行行程 CRUD")
@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripController {
    private final TripService tripService;

    @Operation(summary = "获取行程列表")
    @GetMapping
    public Result<PageResult<Trip>> findAll(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return Result.success(tripService.findAll(userId, status, page, pageSize));
    }

    @Operation(summary = "获取行程详情")
    @GetMapping("/{id}")
    public Result<Trip> findById(@PathVariable Long id) {
        return Result.success(tripService.findById(id));
    }

    @Operation(summary = "创建行程")
    @PostMapping
    public Result<Trip> create(@Valid @RequestBody CreateTripRequest request) {
        return Result.success(tripService.create(UserContext.getUserId(), request));
    }

    @Operation(summary = "更新行程")
    @PutMapping("/{id}")
    public Result<Trip> update(@PathVariable Long id, @Valid @RequestBody UpdateTripRequest request) {
        return Result.success(tripService.update(UserContext.getUserId(), id, request));
    }

    @Operation(summary = "删除行程")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        tripService.delete(UserContext.getUserId(), id);
        return Result.success();
    }
}
