package com.mototrip.mapsafety.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mototrip.common.exception.*;
import com.mototrip.common.response.PageResult;
import com.mototrip.mapsafety.dto.request.CreateDangerZoneRequest;
import com.mototrip.mapsafety.dto.request.UpdateDangerZoneRequest;
import com.mototrip.mapsafety.entity.DangerZone;
import com.mototrip.mapsafety.mapper.DangerZoneMapper;
import com.mototrip.mapsafety.service.DangerZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DangerZoneServiceImpl implements DangerZoneService {

    private final DangerZoneMapper dangerZoneMapper;

    @Override
    public PageResult<DangerZone> findAll(int page, int pageSize) {
        LambdaQueryWrapper<DangerZone> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(DangerZone::getCreatedAt);
        Page<DangerZone> pageResult = dangerZoneMapper.selectPage(new Page<>(page, pageSize), wrapper);
        return PageResult.of(pageResult.getRecords(), pageResult.getTotal(), page, pageSize);
    }

    @Override
    public DangerZone findById(Long id) {
        DangerZone zone = dangerZoneMapper.selectById(id);
        if (zone == null) throw new NotFoundException("危险区域不存在");
        return zone;
    }

    @Override
    public List<DangerZone> findNearby(double lat, double lng, double radius) {
        // 简单实现：查询所有 active 状态的危险区域，在应用层过滤距离
        List<DangerZone> all = dangerZoneMapper.selectList(
                new LambdaQueryWrapper<DangerZone>().eq(DangerZone::getStatus, "active")
        );
        return all.stream().filter(zone -> {
            if (zone.getLocation() == null) return false;
            double zLat = ((Number) zone.getLocation().get("lat")).doubleValue();
            double zLng = ((Number) zone.getLocation().get("lng")).doubleValue();
            double distance = haversine(lat, lng, zLat, zLng);
            return distance <= radius;
        }).toList();
    }

    @Override
    public DangerZone create(Long userId, CreateDangerZoneRequest request) {
        DangerZone zone = new DangerZone();
        zone.setName(request.getName());
        zone.setDescription(request.getDescription());
        zone.setLocation(request.getLocation());
        zone.setRadius(request.getRadius() != null ? request.getRadius() : 500);
        zone.setType(request.getType() != null ? request.getType() : "other");
        zone.setSeverity(request.getSeverity() != null ? request.getSeverity() : "light");
        zone.setStatus("active");
        zone.setImages(request.getImages());
        zone.setReporterId(userId);
        dangerZoneMapper.insert(zone);
        return zone;
    }

    @Override
    public DangerZone update(Long userId, Long id, UpdateDangerZoneRequest request) {
        DangerZone zone = dangerZoneMapper.selectById(id);
        if (zone == null) throw new NotFoundException("危险区域不存在");
        if (!zone.getReporterId().equals(userId)) throw new ForbiddenException("无权修改此危险区域");
        if (request.getName() != null) zone.setName(request.getName());
        if (request.getDescription() != null) zone.setDescription(request.getDescription());
        if (request.getLocation() != null) zone.setLocation(request.getLocation());
        if (request.getRadius() != null) zone.setRadius(request.getRadius());
        if (request.getType() != null) zone.setType(request.getType());
        if (request.getSeverity() != null) zone.setSeverity(request.getSeverity());
        if (request.getImages() != null) zone.setImages(request.getImages());
        dangerZoneMapper.updateById(zone);
        return zone;
    }

    @Override
    public void resolve(Long userId, Long id) {
        DangerZone zone = dangerZoneMapper.selectById(id);
        if (zone == null) throw new NotFoundException("危险区域不存在");
        zone.setStatus("resolved");
        dangerZoneMapper.updateById(zone);
    }

    @Override
    public void ignore(Long userId, Long id) {
        DangerZone zone = dangerZoneMapper.selectById(id);
        if (zone == null) throw new NotFoundException("危险区域不存在");
        zone.setStatus("ignored");
        dangerZoneMapper.updateById(zone);
    }

    @Override
    public void delete(Long userId, Long id) {
        DangerZone zone = dangerZoneMapper.selectById(id);
        if (zone == null) throw new NotFoundException("危险区域不存在");
        if (!zone.getReporterId().equals(userId)) throw new ForbiddenException("无权删除此危险区域");
        dangerZoneMapper.deleteById(id);
    }

    @Override
    public Map<String, Object> getStatistics() {
        long total = dangerZoneMapper.selectCount(null);
        long active = dangerZoneMapper.selectCount(
                new LambdaQueryWrapper<DangerZone>().eq(DangerZone::getStatus, "active")
        );
        long resolved = dangerZoneMapper.selectCount(
                new LambdaQueryWrapper<DangerZone>().eq(DangerZone::getStatus, "resolved")
        );
        long ignored = dangerZoneMapper.selectCount(
                new LambdaQueryWrapper<DangerZone>().eq(DangerZone::getStatus, "ignored")
        );
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", total);
        stats.put("active", active);
        stats.put("resolved", resolved);
        stats.put("ignored", ignored);
        return stats;
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
