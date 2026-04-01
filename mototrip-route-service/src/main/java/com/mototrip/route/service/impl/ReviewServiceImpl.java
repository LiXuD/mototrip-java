package com.mototrip.route.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mototrip.common.exception.ForbiddenException;
import com.mototrip.common.exception.NotFoundException;
import com.mototrip.common.response.PageResult;
import com.mototrip.route.dto.request.CreateReviewRequest;
import com.mototrip.route.dto.request.UpdateReviewRequest;
import com.mototrip.route.entity.Review;
import com.mototrip.route.entity.Route;
import com.mototrip.route.mapper.ReviewMapper;
import com.mototrip.route.mapper.RouteMapper;
import com.mototrip.route.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper reviewMapper;
    private final RouteMapper routeMapper;

    @Override
    @Transactional
    public Review create(Long userId, CreateReviewRequest request) {
        Route route = routeMapper.selectById(request.getRouteId());
        if (route == null) throw new NotFoundException("路线不存在");

        Review review = new Review();
        review.setUserId(userId);
        review.setRouteId(request.getRouteId());
        review.setRating(request.getRating());
        review.setContent(request.getContent());
        reviewMapper.insert(review);

        // 更新路线平均评分和评论数
        updateRouteRating(route);
        return review;
    }

    @Override
    public PageResult<Review> findByRouteId(Long routeId, int page, int pageSize) {
        LambdaQueryWrapper<Review> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Review::getRouteId, routeId).orderByDesc(Review::getCreatedAt);
        Page<Review> pageResult = reviewMapper.selectPage(new Page<>(page, pageSize), wrapper);
        return PageResult.of(pageResult.getRecords(), pageResult.getTotal(), page, pageSize);
    }

    @Override
    public Review findById(Long id) {
        Review review = reviewMapper.selectById(id);
        if (review == null) throw new NotFoundException("评论不存在");
        return review;
    }

    @Override
    public Review update(Long userId, Long id, UpdateReviewRequest request) {
        Review review = reviewMapper.selectById(id);
        if (review == null) throw new NotFoundException("评论不存在");
        if (!review.getUserId().equals(userId)) throw new ForbiddenException("无权修改此评论");
        if (request.getRating() != null) review.setRating(request.getRating());
        if (request.getContent() != null) review.setContent(request.getContent());
        reviewMapper.updateById(review);
        return review;
    }

    @Override
    @Transactional
    public void delete(Long userId, Long id) {
        Review review = reviewMapper.selectById(id);
        if (review == null) throw new NotFoundException("评论不存在");
        if (!review.getUserId().equals(userId)) throw new ForbiddenException("无权删除此评论");
        reviewMapper.deleteById(id);
        Route route = routeMapper.selectById(review.getRouteId());
        if (route != null) updateRouteRating(route);
    }

    private void updateRouteRating(Route route) {
        Long count = reviewMapper.selectCount(
                new LambdaQueryWrapper<Review>().eq(Review::getRouteId, route.getId())
        );
        route.setReviewCount(count.intValue());
        if (count > 0) {
            // 使用 MyBatis-Plus 查询平均评分
            java.util.List<Review> reviews = reviewMapper.selectList(
                    new LambdaQueryWrapper<Review>().eq(Review::getRouteId, route.getId())
            );
            double avg = reviews.stream().mapToInt(Review::getRating).average().orElse(0);
            route.setAvgRating(BigDecimal.valueOf(avg).setScale(2, RoundingMode.HALF_UP));
        } else {
            route.setAvgRating(BigDecimal.ZERO);
        }
        routeMapper.updateById(route);
    }
}
