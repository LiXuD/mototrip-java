package com.mototrip.social.controller;

import com.mototrip.common.context.UserContext;
import com.mototrip.common.response.PageResult;
import com.mototrip.common.response.Result;
import com.mototrip.social.dto.request.CreatePostRequest;
import com.mototrip.social.entity.Comment;
import com.mototrip.social.entity.Post;
import com.mototrip.social.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "帖子管理", description = "社交帖子 CRUD")
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @Operation(summary = "获取帖子列表")
    @GetMapping
    public Result<PageResult<Post>> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return Result.success(postService.findAll(page, pageSize));
    }

    @Operation(summary = "获取帖子详情")
    @GetMapping("/{id}")
    public Result<Post> findById(@PathVariable Long id) {
        return Result.success(postService.findById(id));
    }

    @Operation(summary = "发布帖子")
    @PostMapping
    public Result<Post> create(@Valid @RequestBody CreatePostRequest request) {
        return Result.success(postService.create(UserContext.getUserId(), request));
    }

    @Operation(summary = "删除帖子")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        postService.delete(UserContext.getUserId(), id);
        return Result.success();
    }

    @Operation(summary = "点赞帖子")
    @PostMapping("/{id}/like")
    public Result<Void> like(@PathVariable Long id) {
        postService.like(UserContext.getUserId(), id);
        return Result.success();
    }

    @Operation(summary = "获取帖子评论列表")
    @GetMapping("/{id}/comments")
    public Result<PageResult<Comment>> getComments(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return Result.success(postService.getComments(id, page, pageSize));
    }
}
