package com.mototrip.trip.service;
import com.mototrip.common.response.PageResult;
import com.mototrip.trip.entity.TripShare;

public interface ShareService {
    TripShare create(Long userId, Long tripId);
    TripShare findById(Long id);
    PageResult<TripShare> findMyShares(Long userId, int page, int pageSize);
    void markShared(Long userId, Long id);
    void delete(Long userId, Long id);
}
