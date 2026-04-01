package com.mototrip.mapsafety.service;
import com.mototrip.common.response.PageResult;
import com.mototrip.mapsafety.dto.request.CreateNoParkingZoneRequest;
import com.mototrip.mapsafety.dto.request.UpdateNoParkingZoneRequest;
import com.mototrip.mapsafety.entity.NoParkingZone;
import java.util.List;

public interface NoParkingZoneService {
    PageResult<NoParkingZone> findAll(int page, int pageSize);
    NoParkingZone findById(Long id);
    List<NoParkingZone> findNearby(double lat, double lng, double radius);
    NoParkingZone create(Long userId, CreateNoParkingZoneRequest request);
    NoParkingZone update(Long userId, Long id, UpdateNoParkingZoneRequest request);
    void delete(Long userId, Long id);
}
