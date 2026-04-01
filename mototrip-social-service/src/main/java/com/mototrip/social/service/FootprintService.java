package com.mototrip.social.service;
import com.mototrip.common.response.PageResult;
import com.mototrip.social.dto.request.CreateFootprintRequest;
import com.mototrip.social.entity.Footprint;
import com.mototrip.social.entity.FootprintAchievement;

import java.util.List;
import java.util.Map;

public interface FootprintService {
    PageResult<Footprint> findAll(Long userId, int page, int pageSize);
    List<Map<String, Object>> findMapData(Long userId);
    Footprint create(Long userId, CreateFootprintRequest request);
    void delete(Long userId, Long id);
    List<FootprintAchievement> getAchievements(Long userId);
    Map<String, Object> getStats(Long userId);
}
