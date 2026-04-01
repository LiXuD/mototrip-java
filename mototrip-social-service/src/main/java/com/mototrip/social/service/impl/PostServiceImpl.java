package com.mototrip.social.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mototrip.common.exception.*;
import com.mototrip.common.response.PageResult;
import com.mototrip.social.dto.request.CreatePostRequest;
import com.mototrip.social.entity.*;
import com.mototrip.social.mapper.*;
import com.mototrip.social.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;
    private final CommentMapper commentMapper;
    private final UserLikeMapper userLikeMapper;

    @Override
    public PageResult<Post> findAll(int page, int pageSize) {
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Post::getCreatedAt);
        Page<Post> pageResult = postMapper.selectPage(new Page<>(page, pageSize), wrapper);
        return PageResult.of(pageResult.getRecords(), pageResult.getTotal(), page, pageSize);
    }

    @Override
    public Post findById(Long id) {
        Post post = postMapper.selectById(id);
        if (post == null) throw new NotFoundException("帖子不存在");
        return post;
    }

    @Override
    public Post create(Long userId, CreatePostRequest request) {
        Post post = new Post();
        post.setContent(request.getContent());
        post.setImages(request.getImages());
        post.setLocation(request.getLocation());
        post.setLikes(0);
        post.setComments(0);
        post.setShares(0);
        post.setUserId(userId);
        postMapper.insert(post);
        return post;
    }

    @Override
    public void delete(Long userId, Long id) {
        Post post = postMapper.selectById(id);
        if (post == null) throw new NotFoundException("帖子不存在");
        if (!post.getUserId().equals(userId)) throw new ForbiddenException("无权删除此帖子");
        postMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void like(Long userId, Long id) {
        Post post = postMapper.selectById(id);
        if (post == null) throw new NotFoundException("帖子不存在");
        Long count = userLikeMapper.selectCount(
                new LambdaQueryWrapper<UserLike>()
                        .eq(UserLike::getUserId, userId)
                        .eq(UserLike::getTargetType, "post")
                        .eq(UserLike::getTargetId, id)
        );
        if (count > 0) throw new ConflictException("已经点赞过了");
        UserLike like = new UserLike();
        like.setUserId(userId);
        like.setTargetType("post");
        like.setTargetId(id);
        userLikeMapper.insert(like);
        post.setLikes(post.getLikes() + 1);
        postMapper.updateById(post);
    }

    @Override
    public PageResult<Comment> getComments(Long postId, int page, int pageSize) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getPostId, postId).orderByDesc(Comment::getCreatedAt);
        Page<Comment> pageResult = commentMapper.selectPage(new Page<>(page, pageSize), wrapper);
        return PageResult.of(pageResult.getRecords(), pageResult.getTotal(), page, pageSize);
    }
}
