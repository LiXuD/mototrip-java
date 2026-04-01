package com.mototrip.mapsafety.service;
import com.mototrip.common.response.PageResult;
import com.mototrip.mapsafety.dto.request.CreateOfflineMapRequest;
import com.mototrip.mapsafety.entity.OfflineMap;
import com.mototrip.mapsafety.entity.OfflineMapTile;
import java.util.List;
import java.util.Map;

public interface OfflineMapService {
    PageResult<OfflineMap> findAll(int page, int pageSize);
    OfflineMap findById(Long id);
    OfflineMap create(Long userId, CreateOfflineMapRequest request);
    void delete(Long userId, Long id);
    List<Map<String, Object>> generateTiles(Long id);
    OfflineMapTile getTile(Long mapId, int z, int x, int y);
    void startDownload(Long mapId);
}
