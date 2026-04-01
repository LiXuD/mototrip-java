package com.mototrip.trip.service;
import com.mototrip.common.response.PageResult;
import com.mototrip.trip.dto.request.CreatePreparationRequest;
import com.mototrip.trip.dto.request.UpdatePreparationRequest;
import com.mototrip.trip.entity.Preparation;

public interface PreparationService {
    PageResult<Preparation> findAll(Long userId, String category, int page, int pageSize);
    Preparation create(Long userId, CreatePreparationRequest request);
    Preparation update(Long userId, Long id, UpdatePreparationRequest request);
    void delete(Long userId, Long id);
    void toggle(Long userId, Long id);
}
