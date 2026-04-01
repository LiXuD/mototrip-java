package com.mototrip.trip.controller;

import com.mototrip.common.context.UserContext;
import com.mototrip.common.response.PageResult;
import com.mototrip.common.response.Result;
import com.mototrip.trip.dto.request.CreatePreparationRequest;
import com.mototrip.trip.dto.request.UpdatePreparationRequest;
import com.mototrip.trip.entity.Preparation;
import com.mototrip.trip.service.PreparationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "准备清单", description = "出行准备物品管理")
@RestController
@RequestMapping("/api/preparations")
@RequiredArgsConstructor
public class PreparationController {
    private final PreparationService preparationService;

    @Operation(summary = "获取准备清单列表")
    @GetMapping
    public Result<PageResult<Preparation>> findAll(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return Result.success(preparationService.findAll(userId, category, page, pageSize));
    }

    @Operation(summary = "添加准备物品")
    @PostMapping
    public Result<Preparation> create(@Valid @RequestBody CreatePreparationRequest request) {
        return Result.success(preparationService.create(UserContext.getUserId(), request));
    }

    @Operation(summary = "更新准备物品")
    @PutMapping("/{id}")
    public Result<Preparation> update(@PathVariable Long id, @Valid @RequestBody UpdatePreparationRequest request) {
        return Result.success(preparationService.update(UserContext.getUserId(), id, request));
    }

    @Operation(summary = "删除准备物品")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        preparationService.delete(UserContext.getUserId(), id);
        return Result.success();
    }

    @Operation(summary = "切换物品准备状态")
    @PostMapping("/{id}/toggle")
    public Result<Void> toggle(@PathVariable Long id) {
        preparationService.toggle(UserContext.getUserId(), id);
        return Result.success();
    }
}
