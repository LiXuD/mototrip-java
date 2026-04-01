package com.mototrip.trip.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mototrip.common.exception.ForbiddenException;
import com.mototrip.common.exception.NotFoundException;
import com.mototrip.common.response.PageResult;
import com.mototrip.trip.dto.request.CreateTripRequest;
import com.mototrip.trip.dto.request.UpdateTripRequest;
import com.mototrip.trip.entity.Trip;
import com.mototrip.trip.mapper.TripMapper;
import com.mototrip.trip.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

    private final TripMapper tripMapper;

    @Override
    public PageResult<Trip> findAll(Long userId, String status, int page, int pageSize) {
        LambdaQueryWrapper<Trip> wrapper = new LambdaQueryWrapper<>();
        if (userId != null) wrapper.eq(Trip::getUserId, userId);
        if (status != null && !status.isEmpty()) wrapper.eq(Trip::getStatus, status);
        wrapper.orderByDesc(Trip::getCreatedAt);
        // Trip 模块 pageSize 最大 100
        int effectivePageSize = Math.min(pageSize, 100);
        Page<Trip> pageResult = tripMapper.selectPage(new Page<>(page, effectivePageSize), wrapper);
        return PageResult.of(pageResult.getRecords(), pageResult.getTotal(), page, effectivePageSize);
    }

    @Override
    public Trip findById(Long id) {
        Trip trip = tripMapper.selectById(id);
        if (trip == null) throw new NotFoundException("行程不存在");
        return trip;
    }

    @Override
    public Trip create(Long userId, CreateTripRequest request) {
        Trip trip = new Trip();
        trip.setName(request.getName());
        trip.setDescription(request.getDescription());
        trip.setCoverImage(request.getCoverImage());
        trip.setStartDate(request.getStartDate());
        trip.setEndDate(request.getEndDate());
        trip.setStatus(request.getStatus() != null ? request.getStatus() : "planning");
        trip.setTotalDistance(request.getTotalDistance());
        trip.setNotes(request.getNotes());
        trip.setUserId(userId);
        trip.setRouteId(request.getRouteId());
        tripMapper.insert(trip);
        return trip;
    }

    @Override
    public Trip update(Long userId, Long id, UpdateTripRequest request) {
        Trip trip = tripMapper.selectById(id);
        if (trip == null) throw new NotFoundException("行程不存在");
        if (!trip.getUserId().equals(userId)) throw new ForbiddenException("无权修改此行程");
        if (request.getName() != null) trip.setName(request.getName());
        if (request.getDescription() != null) trip.setDescription(request.getDescription());
        if (request.getCoverImage() != null) trip.setCoverImage(request.getCoverImage());
        if (request.getStartDate() != null) trip.setStartDate(request.getStartDate());
        if (request.getEndDate() != null) trip.setEndDate(request.getEndDate());
        if (request.getStatus() != null) trip.setStatus(request.getStatus());
        if (request.getTotalDistance() != null) trip.setTotalDistance(request.getTotalDistance());
        if (request.getNotes() != null) trip.setNotes(request.getNotes());
        if (request.getRouteId() != null) trip.setRouteId(request.getRouteId());
        tripMapper.updateById(trip);
        return trip;
    }

    @Override
    public void delete(Long userId, Long id) {
        Trip trip = tripMapper.selectById(id);
        if (trip == null) throw new NotFoundException("行程不存在");
        if (!trip.getUserId().equals(userId)) throw new ForbiddenException("无权删除此行程");
        tripMapper.deleteById(id);
    }
}
