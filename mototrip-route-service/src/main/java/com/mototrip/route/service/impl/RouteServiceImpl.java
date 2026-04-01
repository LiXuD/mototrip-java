package com.mototrip.route.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mototrip.common.exception.ConflictException;
import com.mototrip.common.exception.ForbiddenException;
import com.mototrip.common.exception.NotFoundException;
import com.mototrip.common.response.PageResult;
import com.mototrip.route.dto.request.CreateRouteRequest;
import com.mototrip.route.dto.request.UpdateRouteRequest;
import com.mototrip.route.entity.Route;
import com.mototrip.route.entity.UserLike;
import com.mototrip.route.mapper.RouteMapper;
import com.mototrip.route.mapper.UserLikeMapper;
import com.mototrip.route.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteMapper routeMapper;
    private final UserLikeMapper userLikeMapper;

    @Override
    public PageResult<Route> findAll(String difficulty, String keyword, int page, int pageSize) {
        LambdaQueryWrapper<Route> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Route::getIsPublic, true);
        if (difficulty != null && !difficulty.isEmpty()) {
            wrapper.eq(Route::getDifficulty, difficulty);
        }
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(Route::getName, keyword);
        }
        wrapper.orderByDesc(Route::getCreatedAt);
        Page<Route> pageResult = routeMapper.selectPage(new Page<>(page, pageSize), wrapper);
        return PageResult.of(pageResult.getRecords(), pageResult.getTotal(), page, pageSize);
    }

    @Override
    public Route findById(Long id) {
        Route route = routeMapper.selectById(id);
        if (route == null) {
            throw new NotFoundException("路线不存在");
        }
        // 增加浏览量
        route.setViews(route.getViews() + 1);
        routeMapper.updateById(route);
        return route;
    }

    @Override
    public Route create(Long userId, CreateRouteRequest request) {
        Route route = new Route();
        route.setName(request.getName());
        route.setDescription(request.getDescription());
        route.setCoverImage(request.getCoverImage());
        route.setDistance(request.getDistance());
        route.setDuration(request.getDuration());
        route.setDifficulty(request.getDifficulty() != null ? request.getDifficulty() : "easy");
        route.setStartPoint(request.getStartPoint());
        route.setEndPoint(request.getEndPoint());
        route.setIsPublic(request.getIsPublic() != null ? request.getIsPublic() : true);
        route.setLikes(0);
        route.setViews(0);
        route.setIsOfficial(false);
        route.setAvgRating(java.math.BigDecimal.ZERO);
        route.setReviewCount(0);
        route.setSeasons(request.getSeasons());
        route.setMotorcycleTypes(request.getMotorcycleTypes());
        route.setCreatorId(userId);
        routeMapper.insert(route);
        return route;
    }

    @Override
    public Route update(Long userId, Long id, UpdateRouteRequest request) {
        Route route = routeMapper.selectById(id);
        if (route == null) throw new NotFoundException("路线不存在");
        if (!route.getCreatorId().equals(userId)) throw new ForbiddenException("无权修改此路线");

        if (request.getName() != null) route.setName(request.getName());
        if (request.getDescription() != null) route.setDescription(request.getDescription());
        if (request.getCoverImage() != null) route.setCoverImage(request.getCoverImage());
        if (request.getDistance() != null) route.setDistance(request.getDistance());
        if (request.getDuration() != null) route.setDuration(request.getDuration());
        if (request.getDifficulty() != null) route.setDifficulty(request.getDifficulty());
        if (request.getStartPoint() != null) route.setStartPoint(request.getStartPoint());
        if (request.getEndPoint() != null) route.setEndPoint(request.getEndPoint());
        if (request.getIsPublic() != null) route.setIsPublic(request.getIsPublic());
        if (request.getSeasons() != null) route.setSeasons(request.getSeasons());
        if (request.getMotorcycleTypes() != null) route.setMotorcycleTypes(request.getMotorcycleTypes());
        routeMapper.updateById(route);
        return route;
    }

    @Override
    public void delete(Long userId, Long id) {
        Route route = routeMapper.selectById(id);
        if (route == null) throw new NotFoundException("路线不存在");
        if (!route.getCreatorId().equals(userId)) throw new ForbiddenException("无权删除此路线");
        routeMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void like(Long userId, Long id) {
        Route route = routeMapper.selectById(id);
        if (route == null) throw new NotFoundException("路线不存在");

        // 检查是否已点赞
        Long count = userLikeMapper.selectCount(
                new LambdaQueryWrapper<UserLike>()
                        .eq(UserLike::getUserId, userId)
                        .eq(UserLike::getTargetType, "route")
                        .eq(UserLike::getTargetId, id)
        );
        if (count > 0) {
            throw new ConflictException("已经点赞过了");
        }

        // 创建点赞记录
        UserLike like = new UserLike();
        like.setUserId(userId);
        like.setTargetType("route");
        like.setTargetId(id);
        userLikeMapper.insert(like);

        // 更新点赞数
        route.setLikes(route.getLikes() + 1);
        routeMapper.updateById(route);
    }
}
