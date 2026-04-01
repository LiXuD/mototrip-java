package com.mototrip.trip.service;
import com.mototrip.common.response.PageResult;
import com.mototrip.trip.dto.request.CreateDiaryRequest;
import com.mototrip.trip.dto.request.UpdateDiaryRequest;
import com.mototrip.trip.entity.Diary;

public interface DiaryService {
    PageResult<Diary> findAll(Long userId, Long tripId, int page, int pageSize);
    Diary findById(Long id);
    Diary create(Long userId, CreateDiaryRequest request);
    Diary update(Long userId, Long id, UpdateDiaryRequest request);
    void delete(Long userId, Long id);
    void like(Long userId, Long id);
}
