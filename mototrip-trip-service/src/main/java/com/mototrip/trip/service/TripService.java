package com.mototrip.trip.service;
import com.mototrip.common.response.PageResult;
import com.mototrip.trip.dto.request.CreateTripRequest;
import com.mototrip.trip.dto.request.UpdateTripRequest;
import com.mototrip.trip.entity.Trip;

public interface TripService {
    PageResult<Trip> findAll(Long userId, String status, int page, int pageSize);
    Trip findById(Long id);
    Trip create(Long userId, CreateTripRequest request);
    Trip update(Long userId, Long id, UpdateTripRequest request);
    void delete(Long userId, Long id);
}
