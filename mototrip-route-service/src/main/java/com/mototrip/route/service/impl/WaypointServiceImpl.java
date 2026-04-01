package com.mototrip.route.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mototrip.common.exception.ForbiddenException;
import com.mototrip.common.exception.NotFoundException;
import com.mototrip.common.response.PageResult;
import com.mototrip.route.dto.request.CreateWaypointRequest;
import com.mototrip.route.dto.request.UpdateWaypointRequest;
import com.mototrip.route.entity.Route;
import com.mototrip.route.entity.Waypoint;
import com.mototrip.route.mapper.RouteMapper;
import com.mototrip.route.mapper.WaypointMapper;
import com.mototrip.route.service.WaypointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WaypointServiceImpl implements WaypointService {

    private final WaypointMapper waypointMapper;
    private final RouteMapper routeMapper;

    @Override
    public PageResult<Waypoint> findAll(Long routeId, Long tripId, int page, int pageSize) {
        LambdaQueryWrapper<Waypoint> wrapper = new LambdaQueryWrapper<>();
        if (routeId != null) wrapper.eq(Waypoint::getRouteId, routeId);
        if (tripId != null) wrapper.eq(Waypoint::getTripId, tripId);
        wrapper.orderByAsc(Waypoint::getSequence);
        Page<Waypoint> pageResult = waypointMapper.selectPage(new Page<>(page, pageSize), wrapper);
        return PageResult.of(pageResult.getRecords(), pageResult.getTotal(), page, pageSize);
    }

    @Override
    public Waypoint findById(Long id) {
        Waypoint waypoint = waypointMapper.selectById(id);
        if (waypoint == null) throw new NotFoundException("途经点不存在");
        return waypoint;
    }

    @Override
    public Waypoint create(Long userId, CreateWaypointRequest request) {
        // 验证路线所有权
        if (request.getRouteId() != null) {
            verifyRouteOwnership(userId, request.getRouteId());
        }
        Waypoint waypoint = new Waypoint();
        waypoint.setName(request.getName());
        waypoint.setDescription(request.getDescription());
        waypoint.setType(request.getType() != null ? request.getType() : "other");
        waypoint.setLocation(request.getLocation());
        waypoint.setImages(request.getImages());
        waypoint.setRating(request.getRating());
        waypoint.setPhone(request.getPhone());
        waypoint.setOpeningHours(request.getOpeningHours());
        waypoint.setSequence(request.getSequence() != null ? request.getSequence() : 0);
        waypoint.setMetadata(request.getMetadata());
        waypoint.setRouteId(request.getRouteId());
        waypoint.setTripId(request.getTripId());
        waypointMapper.insert(waypoint);
        return waypoint;
    }

    @Override
    public Waypoint update(Long userId, Long id, UpdateWaypointRequest request) {
        Waypoint waypoint = waypointMapper.selectById(id);
        if (waypoint == null) throw new NotFoundException("途经点不存在");
        if (waypoint.getRouteId() != null) {
            verifyRouteOwnership(userId, waypoint.getRouteId());
        }
        if (request.getName() != null) waypoint.setName(request.getName());
        if (request.getDescription() != null) waypoint.setDescription(request.getDescription());
        if (request.getType() != null) waypoint.setType(request.getType());
        if (request.getLocation() != null) waypoint.setLocation(request.getLocation());
        if (request.getImages() != null) waypoint.setImages(request.getImages());
        if (request.getRating() != null) waypoint.setRating(request.getRating());
        if (request.getPhone() != null) waypoint.setPhone(request.getPhone());
        if (request.getOpeningHours() != null) waypoint.setOpeningHours(request.getOpeningHours());
        if (request.getSequence() != null) waypoint.setSequence(request.getSequence());
        if (request.getMetadata() != null) waypoint.setMetadata(request.getMetadata());
        if (request.getRouteId() != null) waypoint.setRouteId(request.getRouteId());
        if (request.getTripId() != null) waypoint.setTripId(request.getTripId());
        waypointMapper.updateById(waypoint);
        return waypoint;
    }

    @Override
    public void delete(Long userId, Long id) {
        Waypoint waypoint = waypointMapper.selectById(id);
        if (waypoint == null) throw new NotFoundException("途经点不存在");
        if (waypoint.getRouteId() != null) {
            verifyRouteOwnership(userId, waypoint.getRouteId());
        }
        waypointMapper.deleteById(id);
    }

    private void verifyRouteOwnership(Long userId, Long routeId) {
        Route route = routeMapper.selectById(routeId);
        if (route == null) throw new NotFoundException("关联路线不存在");
        if (!route.getCreatorId().equals(userId)) throw new ForbiddenException("无权操作此路线的途经点");
    }
}
