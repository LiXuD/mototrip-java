package com.mototrip.social.service;
import com.mototrip.common.response.PageResult;
import com.mototrip.social.dto.request.CreatePostRequest;
import com.mototrip.social.entity.Comment;
import com.mototrip.social.entity.Post;

public interface PostService {
    PageResult<Post> findAll(int page, int pageSize);
    Post findById(Long id);
    Post create(Long userId, CreatePostRequest request);
    void delete(Long userId, Long id);
    void like(Long userId, Long id);
    PageResult<Comment> getComments(Long postId, int page, int pageSize);
}
