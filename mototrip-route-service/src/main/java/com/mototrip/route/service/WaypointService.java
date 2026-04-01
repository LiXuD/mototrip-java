package com.mototrip.route.service;

import com.mototrip.common.response.PageResult;
import com.mototrip.route.dto.request.CreateWaypointRequest;
import com.mototrip.route.dto.request.UpdateWaypointRequest;
import com.mototrip.route.entity.Waypoint;

public interface WaypointService {
    PageResult<Waypoint> findAll(Long routeId, Long tripId, int page, int pageSize);
    Waypoint findById(Long id);
    Waypoint create(Long userId, CreateWaypointRequest request);
    Waypoint update(Long userId, Long id, UpdateWaypointRequest request);
    void delete(Long userId, Long id);
}
