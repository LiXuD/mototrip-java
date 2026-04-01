package com.mototrip.social.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mototrip.common.exception.ForbiddenException;
import com.mototrip.common.exception.NotFoundException;
import com.mototrip.common.response.PageResult;
import com.mototrip.social.dto.request.CreateFootprintRequest;
import com.mototrip.social.entity.Footprint;
import com.mototrip.social.entity.FootprintAchievement;
import com.mototrip.social.mapper.FootprintAchievementMapper;
import com.mototrip.social.mapper.FootprintMapper;
import com.mototrip.social.service.FootprintService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FootprintServiceImpl implements FootprintService {

    private final FootprintMapper footprintMapper;
    private final FootprintAchievementMapper achievementMapper;

    @Override
    public PageResult<Footprint> findAll(Long userId, int page, int pageSize) {
        LambdaQueryWrapper<Footprint> wrapper = new LambdaQueryWrapper<>();
        if (userId != null) wrapper.eq(Footprint::getUserId, userId);
        wrapper.orderByDesc(Footprint::getVisitedAt);
        Page<Footprint> pageResult = footprintMapper.selectPage(new Page<>(page, pageSize), wrapper);
        return PageResult.of(pageResult.getRecords(), pageResult.getTotal(), page, pageSize);
    }

    @Override
    public List<Map<String, Object>> findMapData(Long userId) {
        List<Footprint> footprints = footprintMapper.selectList(
                new LambdaQueryWrapper<Footprint>().eq(Footprint::getUserId, userId)
        );
        return footprints.stream().map(fp -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", fp.getId());
            map.put("latitude", fp.getLatitude());
            map.put("longitude", fp.getLongitude());
            map.put("locationName", fp.getLocationName());
            map.put("image", fp.getImage());
            return map;
        }).collect(Collectors.toList());
    }

    @Override
    public Footprint create(Long userId, CreateFootprintRequest request) {
        Footprint footprint = new Footprint();
        footprint.setUserId(userId);
        footprint.setLatitude(request.getLatitude());
        footprint.setLongitude(request.getLongitude());
        footprint.setLocationName(request.getLocationName());
        footprint.setProvince(request.getProvince());
        footprint.setCity(request.getCity());
        footprint.setDistrict(request.getDistrict());
        footprint.setImage(request.getImage());
        footprint.setNote(request.getNote());
        footprint.setVisitCount(0);
        footprint.setDistance(request.getDistance());
        footprint.setVisitedAt(LocalDateTime.now());
        footprintMapper.insert(footprint);
        return footprint;
    }

    @Override
    public void delete(Long userId, Long id) {
        Footprint footprint = footprintMapper.selectById(id);
        if (footprint == null) throw new NotFoundException("足迹不存在");
        if (!footprint.getUserId().equals(userId)) throw new ForbiddenException("无权删除此足迹");
        footprintMapper.deleteById(id);
    }

    @Override
    public List<FootprintAchievement> getAchievements(Long userId) {
        return achievementMapper.selectList(
                new LambdaQueryWrapper<FootprintAchievement>().eq(FootprintAchievement::getUserId, userId)
        );
    }

    @Override
    public Map<String, Object> getStats(Long userId) {
        List<Footprint> footprints = footprintMapper.selectList(
                new LambdaQueryWrapper<Footprint>().eq(Footprint::getUserId, userId)
        );
        long totalCount = footprints.size();
        long provinceCount = footprints.stream()
                .map(Footprint::getProvince)
                .filter(Objects::nonNull)
                .distinct()
                .count();
        long cityCount = footprints.stream()
                .map(Footprint::getCity)
                .filter(Objects::nonNull)
                .distinct()
                .count();
        double totalDistance = footprints.stream()
                .mapToDouble(fp -> fp.getDistance() != null ? fp.getDistance().doubleValue() : 0)
                .sum();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalFootprints", totalCount);
        stats.put("provinceCount", provinceCount);
        stats.put("cityCount", cityCount);
        stats.put("totalDistance", totalDistance);
        return stats;
    }
}
