package com.mototrip.route.service;

import com.mototrip.common.response.PageResult;
import com.mototrip.route.dto.request.CreateReviewRequest;
import com.mototrip.route.dto.request.UpdateReviewRequest;
import com.mototrip.route.entity.Review;

public interface ReviewService {
    Review create(Long userId, CreateReviewRequest request);
    PageResult<Review> findByRouteId(Long routeId, int page, int pageSize);
    Review findById(Long id);
    Review update(Long userId, Long id, UpdateReviewRequest request);
    void delete(Long userId, Long id);
}
