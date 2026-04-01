package com.mototrip.mapsafety.service;
import com.mototrip.common.response.PageResult;
import com.mototrip.mapsafety.dto.request.CreateDangerZoneRequest;
import com.mototrip.mapsafety.dto.request.UpdateDangerZoneRequest;
import com.mototrip.mapsafety.entity.DangerZone;
import java.util.Map;

public interface DangerZoneService {
    PageResult<DangerZone> findAll(int page, int pageSize);
    DangerZone findById(Long id);
    java.util.List<DangerZone> findNearby(double lat, double lng, double radius);
    DangerZone create(Long userId, CreateDangerZoneRequest request);
    DangerZone update(Long userId, Long id, UpdateDangerZoneRequest request);
    void resolve(Long userId, Long id);
    void ignore(Long userId, Long id);
    void delete(Long userId, Long id);
    Map<String, Object> getStatistics();
}
