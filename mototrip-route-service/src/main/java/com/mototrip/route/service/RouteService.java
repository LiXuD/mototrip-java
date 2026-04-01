package com.mototrip.route.service;

import com.mototrip.common.response.PageResult;
import com.mototrip.route.dto.request.CreateRouteRequest;
import com.mototrip.route.dto.request.UpdateRouteRequest;
import com.mototrip.route.entity.Route;

public interface RouteService {
    PageResult<Route> findAll(String difficulty, String keyword, int page, int pageSize);
    Route findById(Long id);
    Route create(Long userId, CreateRouteRequest request);
    Route update(Long userId, Long id, UpdateRouteRequest request);
    void delete(Long userId, Long id);
    void like(Long userId, Long id);
}
