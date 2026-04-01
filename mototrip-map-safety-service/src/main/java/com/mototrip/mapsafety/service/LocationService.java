package com.mototrip.mapsafety.service;
import com.mototrip.common.response.PageResult;
import com.mototrip.mapsafety.dto.request.CreateLocationShareRequest;
import com.mototrip.mapsafety.dto.request.UpdateLocationRequest;
import com.mototrip.mapsafety.entity.LocationShare;
import com.mototrip.mapsafety.entity.LocationUpdate;

public interface LocationService {
    PageResult<LocationShare> findSharedWithMe(Long userId, int page, int pageSize);
    PageResult<LocationShare> findMyShares(Long userId, int page, int pageSize);
    LocationShare findById(Long id);
    PageResult<LocationUpdate> getHistory(Long shareId, int hours);
    LocationShare createShare(Long userId, CreateLocationShareRequest request);
    void updateLocation(Long userId, Long shareId, UpdateLocationRequest request);
    void stopSharing(Long userId, Long shareId);
    void delete(Long userId, Long shareId);
}
