package com.mototrip.trip.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mototrip.common.exception.ForbiddenException;
import com.mototrip.common.exception.NotFoundException;
import com.mototrip.common.response.PageResult;
import com.mototrip.trip.entity.Trip;
import com.mototrip.trip.entity.TripShare;
import com.mototrip.trip.mapper.TripMapper;
import com.mototrip.trip.mapper.TripShareMapper;
import com.mototrip.trip.service.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShareServiceImpl implements ShareService {

    private final TripShareMapper tripShareMapper;
    private final TripMapper tripMapper;

    @Override
    public TripShare create(Long userId, Long tripId) {
        Trip trip = tripMapper.selectById(tripId);
        if (trip == null) throw new NotFoundException("行程不存在");

        TripShare share = new TripShare();
        share.setUserId(userId);
        share.setTripId(tripId);
        share.setTitle(trip.getName());
        share.setSummary(trip.getDescription());
        share.setTotalDistance(trip.getTotalDistance());
        share.setIsShared(false);
        tripShareMapper.insert(share);
        return share;
    }

    @Override
    public TripShare findById(Long id) {
        TripShare share = tripShareMapper.selectById(id);
        if (share == null) throw new NotFoundException("分享不存在");
        return share;
    }

    @Override
    public PageResult<TripShare> findMyShares(Long userId, int page, int pageSize) {
        LambdaQueryWrapper<TripShare> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TripShare::getUserId, userId).orderByDesc(TripShare::getCreatedAt);
        Page<TripShare> pageResult = tripShareMapper.selectPage(new Page<>(page, pageSize), wrapper);
        return PageResult.of(pageResult.getRecords(), pageResult.getTotal(), page, pageSize);
    }

    @Override
    public void markShared(Long userId, Long id) {
        TripShare share = tripShareMapper.selectById(id);
        if (share == null) throw new NotFoundException("分享不存在");
        if (!share.getUserId().equals(userId)) throw new ForbiddenException("无权操作此分享");
        share.setIsShared(true);
        tripShareMapper.updateById(share);
    }

    @Override
    public void delete(Long userId, Long id) {
        TripShare share = tripShareMapper.selectById(id);
        if (share == null) throw new NotFoundException("分享不存在");
        if (!share.getUserId().equals(userId)) throw new ForbiddenException("无权删除此分享");
        tripShareMapper.deleteById(id);
    }
}
