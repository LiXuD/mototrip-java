package com.mototrip.trip.controller;

import com.mototrip.common.context.UserContext;
import com.mototrip.common.response.PageResult;
import com.mototrip.common.response.Result;
import com.mototrip.trip.dto.request.CreateDiaryRequest;
import com.mototrip.trip.dto.request.UpdateDiaryRequest;
import com.mototrip.trip.entity.Diary;
import com.mototrip.trip.service.DiaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "日记管理", description = "旅行日记 CRUD")
@RestController
@RequestMapping("/api/diaries")
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;

    @Operation(summary = "获取日记列表")
    @GetMapping
    public Result<PageResult<Diary>> findAll(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long tripId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return Result.success(diaryService.findAll(userId, tripId, page, pageSize));
    }

    @Operation(summary = "获取日记详情")
    @GetMapping("/{id}")
    public Result<Diary> findById(@PathVariable Long id) {
        return Result.success(diaryService.findById(id));
    }

    @Operation(summary = "创建日记")
    @PostMapping
    public Result<Diary> create(@Valid @RequestBody CreateDiaryRequest request) {
        return Result.success(diaryService.create(UserContext.getUserId(), request));
    }

    @Operation(summary = "更新日记")
    @PutMapping("/{id}")
    public Result<Diary> update(@PathVariable Long id, @Valid @RequestBody UpdateDiaryRequest request) {
        return Result.success(diaryService.update(UserContext.getUserId(), id, request));
    }

    @Operation(summary = "删除日记")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        diaryService.delete(UserContext.getUserId(), id);
        return Result.success();
    }

    @Operation(summary = "点赞日记")
    @PostMapping("/{id}/like")
    public Result<Void> like(@PathVariable Long id) {
        diaryService.like(UserContext.getUserId(), id);
        return Result.success();
    }
}
