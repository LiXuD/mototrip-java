package com.mototrip.route.controller;

import com.mototrip.common.context.UserContext;
import com.mototrip.common.response.PageResult;
import com.mototrip.common.response.Result;
import com.mototrip.route.dto.request.CreateReviewRequest;
import com.mototrip.route.dto.request.UpdateReviewRequest;
import com.mototrip.route.entity.Review;
import com.mototrip.route.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "评论管理", description = "评论创建、查询、更新、删除")
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "创建评论")
    @PostMapping
    public Result<Review> create(@Valid @RequestBody CreateReviewRequest request) {
        Long userId = UserContext.getUserId();
        return Result.success(reviewService.create(userId, request));
    }

    @Operation(summary = "获取路线评论列表")
    @GetMapping
    public Result<PageResult<Review>> findByRouteId(
            @RequestParam(name = "routeId") Long routeId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return Result.success(reviewService.findByRouteId(routeId, page, pageSize));
    }

    @Operation(summary = "获取评论详情")
    @GetMapping("/{id}")
    public Result<Review> findById(@PathVariable("id") Long id) {
        return Result.success(reviewService.findById(id));
    }

    @Operation(summary = "更新评论")
    @PutMapping("/{id}")
    public Result<Review> update(@PathVariable("id") Long id, @Valid @RequestBody UpdateReviewRequest request) {
        Long userId = UserContext.getUserId();
        return Result.success(reviewService.update(userId, id, request));
    }

    @Operation(summary = "删除评论")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable("id") Long id) {
        Long userId = UserContext.getUserId();
        reviewService.delete(userId, id);
        return Result.success();
    }
}
