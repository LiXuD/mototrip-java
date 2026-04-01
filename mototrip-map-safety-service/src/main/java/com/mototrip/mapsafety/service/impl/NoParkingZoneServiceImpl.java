package com.mototrip.mapsafety.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mototrip.common.exception.*;
import com.mototrip.common.response.PageResult;
import com.mototrip.mapsafety.dto.request.CreateNoParkingZoneRequest;
import com.mototrip.mapsafety.dto.request.UpdateNoParkingZoneRequest;
import com.mototrip.mapsafety.entity.NoParkingZone;
import com.mototrip.mapsafety.mapper.NoParkingZoneMapper;
import com.mototrip.mapsafety.service.NoParkingZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoParkingZoneServiceImpl implements NoParkingZoneService {

    private final NoParkingZoneMapper noParkingZoneMapper;

    @Override
    public PageResult<NoParkingZone> findAll(int page, int pageSize) {
        LambdaQueryWrapper<NoParkingZone> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(NoParkingZone::getCreatedAt);
        Page<NoParkingZone> pageResult = noParkingZoneMapper.selectPage(new Page<>(page, pageSize), wrapper);
        return PageResult.of(pageResult.getRecords(), pageResult.getTotal(), page, pageSize);
    }

    @Override
    public NoParkingZone findById(Long id) {
        NoParkingZone zone = noParkingZoneMapper.selectById(id);
        if (zone == null) throw new NotFoundException("禁停区域不存在");
        return zone;
    }

    @Override
    public List<NoParkingZone> findNearby(double lat, double lng, double radius) {
        List<NoParkingZone> all = noParkingZoneMapper.selectList(null);
        return all.stream().filter(zone -> {
            if (zone.getLocation() == null) return false;
            double zLat = ((Number) zone.getLocation().get("lat")).doubleValue();
            double zLng = ((Number) zone.getLocation().get("lng")).doubleValue();
            double distance = haversine(lat, lng, zLat, zLng);
            return distance <= radius;
        }).toList();
    }

    @Override
    public NoParkingZone create(Long userId, CreateNoParkingZoneRequest request) {
        NoParkingZone zone = new NoParkingZone();
        zone.setName(request.getName());
        zone.setDescription(request.getDescription());
        zone.setLocation(request.getLocation());
        zone.setRadius(request.getRadius() != null ? request.getRadius() : 500);
        zone.setImages(request.getImages());
        zone.setReporterId(userId);
        noParkingZoneMapper.insert(zone);
        return zone;
    }

    @Override
    public NoParkingZone update(Long userId, Long id, UpdateNoParkingZoneRequest request) {
        NoParkingZone zone = noParkingZoneMapper.selectById(id);
        if (zone == null) throw new NotFoundException("禁停区域不存在");
        if (!zone.getReporterId().equals(userId)) throw new ForbiddenException("无权修改此禁停区域");
        if (request.getName() != null) zone.setName(request.getName());
        if (request.getDescription() != null) zone.setDescription(request.getDescription());
        if (request.getLocation() != null) zone.setLocation(request.getLocation());
        if (request.getRadius() != null) zone.setRadius(request.getRadius());
        if (request.getImages() != null) zone.setImages(request.getImages());
        noParkingZoneMapper.updateById(zone);
        return zone;
    }

    @Override
    public void delete(Long userId, Long id) {
        NoParkingZone zone = noParkingZoneMapper.selectById(id);
        if (zone == null) throw new NotFoundException("禁停区域不存在");
        if (!zone.getReporterId().equals(userId)) throw new ForbiddenException("无权删除此禁停区域");
        noParkingZoneMapper.deleteById(id);
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }
}
