package com.mototrip.mapsafety.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mototrip.common.exception.ForbiddenException;
import com.mototrip.common.exception.NotFoundException;
import com.mototrip.common.response.PageResult;
import com.mototrip.mapsafety.dto.request.CreateLocationShareRequest;
import com.mototrip.mapsafety.dto.request.UpdateLocationRequest;
import com.mototrip.mapsafety.entity.LocationShare;
import com.mototrip.mapsafety.entity.LocationUpdate;
import com.mototrip.mapsafety.mapper.LocationShareMapper;
import com.mototrip.mapsafety.mapper.LocationUpdateMapper;
import com.mototrip.mapsafety.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationShareMapper locationShareMapper;
    private final LocationUpdateMapper locationUpdateMapper;

    @Override
    public PageResult<LocationShare> findSharedWithMe(Long userId, int page, int pageSize) {
        // 查找与我共享的位置（通过 share_code 关联）
        LambdaQueryWrapper<LocationShare> wrapper = new LambdaQueryWrapper<>();
        wrapper.ne(LocationShare::getUserId, userId)
                .eq(LocationShare::getIsActive, true)
                .orderByDesc(LocationShare::getCreatedAt);
        Page<LocationShare> pageResult = locationShareMapper.selectPage(new Page<>(page, pageSize), wrapper);
        return PageResult.of(pageResult.getRecords(), pageResult.getTotal(), page, pageSize);
    }

    @Override
    public PageResult<LocationShare> findMyShares(Long userId, int page, int pageSize) {
        LambdaQueryWrapper<LocationShare> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LocationShare::getUserId, userId).orderByDesc(LocationShare::getCreatedAt);
        Page<LocationShare> pageResult = locationShareMapper.selectPage(new Page<>(page, pageSize), wrapper);
        return PageResult.of(pageResult.getRecords(), pageResult.getTotal(), page, pageSize);
    }

    @Override
    public LocationShare findById(Long id) {
        LocationShare share = locationShareMapper.selectById(id);
        if (share == null) throw new NotFoundException("位置共享不存在");
        return share;
    }

    @Override
    public PageResult<LocationUpdate> getHistory(Long shareId, int hours) {
        if (hours <= 0) hours = 24;
        int limit = 100;
        LocalDateTime since = LocalDateTime.now().minusHours(hours);
        LambdaQueryWrapper<LocationUpdate> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LocationUpdate::getShareId, shareId)
                .ge(LocationUpdate::getRecordedAt, since)
                .orderByDesc(LocationUpdate::getRecordedAt)
                .last("LIMIT " + limit);
        Page<LocationUpdate> pageResult = locationUpdateMapper.selectPage(
                new Page<>(1, limit), wrapper);
        return PageResult.of(pageResult.getRecords(), pageResult.getTotal(), 1, limit);
    }

    @Override
    public LocationShare createShare(Long userId, CreateLocationShareRequest request) {
        LocationShare share = new LocationShare();
        share.setUserId(userId);
        share.setShareCode(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        share.setIsActive(true);
        share.setExpiresAt(request.getExpiresAt());
        locationShareMapper.insert(share);
        return share;
    }

    @Override
    public void updateLocation(Long userId, Long shareId, UpdateLocationRequest request) {
        LocationShare share = locationShareMapper.selectById(shareId);
        if (share == null) throw new NotFoundException("位置共享不存在");
        if (!share.getUserId().equals(userId)) throw new ForbiddenException("无权更新此位置");

        LocationUpdate update = new LocationUpdate();
        update.setShareId(shareId);
        update.setLatitude(request.getLatitude());
        update.setLongitude(request.getLongitude());
        update.setSpeed(request.getSpeed());
        update.setHeading(request.getHeading());
        update.setRecordedAt(LocalDateTime.now());
        locationUpdateMapper.insert(update);
    }

    @Override
    public void stopSharing(Long userId, Long shareId) {
        LocationShare share = locationShareMapper.selectById(shareId);
        if (share == null) throw new NotFoundException("位置共享不存在");
        if (!share.getUserId().equals(userId)) throw new ForbiddenException("无权操作此位置共享");
        share.setIsActive(false);
        locationShareMapper.updateById(share);
    }

    @Override
    public void delete(Long userId, Long shareId) {
        LocationShare share = locationShareMapper.selectById(shareId);
        if (share == null) throw new NotFoundException("位置共享不存在");
        if (!share.getUserId().equals(userId)) throw new ForbiddenException("无权删除此位置共享");
        locationUpdateMapper.delete(
                new LambdaQueryWrapper<LocationUpdate>().eq(LocationUpdate::getShareId, shareId)
        );
        locationShareMapper.deleteById(shareId);
    }
}
