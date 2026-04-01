package com.mototrip.route.controller;

import com.mototrip.common.context.UserContext;
import com.mototrip.common.response.PageResult;
import com.mototrip.common.response.Result;
import com.mototrip.route.dto.request.CreateRouteRequest;
import com.mototrip.route.dto.request.UpdateRouteRequest;
import com.mototrip.route.entity.Route;
import com.mototrip.route.service.RouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "路线管理", description = "骑行路线 CRUD")
@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @Operation(summary = "获取路线列表")
    @GetMapping
    public Result<PageResult<Route>> findAll(
            @RequestParam(name = "difficulty", required = false) String difficulty,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return Result.success(routeService.findAll(difficulty, keyword, page, pageSize));
    }

    @Operation(summary = "获取路线详情")
    @GetMapping("/{id}")
    public Result<Route> findById(@PathVariable("id") Long id) {
        return Result.success(routeService.findById(id));
    }

    @Operation(summary = "创建路线")
    @PostMapping
    public Result<Route> create(@Valid @RequestBody CreateRouteRequest request) {
        Long userId = UserContext.getUserId();
        return Result.success(routeService.create(userId, request));
    }

    @Operation(summary = "更新路线")
    @PutMapping("/{id}")
    public Result<Route> update(@PathVariable("id") Long id, @Valid @RequestBody UpdateRouteRequest request) {
        Long userId = UserContext.getUserId();
        return Result.success(routeService.update(userId, id, request));
    }

    @Operation(summary = "删除路线")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable("id") Long id) {
        Long userId = UserContext.getUserId();
        routeService.delete(userId, id);
        return Result.success();
    }

    @Operation(summary = "点赞路线")
    @PostMapping("/{id}/like")
    public Result<Void> like(@PathVariable("id") Long id) {
        Long userId = UserContext.getUserId();
        routeService.like(userId, id);
        return Result.success();
    }
}
