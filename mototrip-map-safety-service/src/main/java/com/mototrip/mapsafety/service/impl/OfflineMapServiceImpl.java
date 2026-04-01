package com.mototrip.mapsafety.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mototrip.common.exception.ForbiddenException;
import com.mototrip.common.exception.NotFoundException;
import com.mototrip.common.response.PageResult;
import com.mototrip.mapsafety.dto.request.CreateOfflineMapRequest;
import com.mototrip.mapsafety.entity.OfflineMap;
import com.mototrip.mapsafety.entity.OfflineMapTile;
import com.mototrip.mapsafety.mapper.OfflineMapMapper;
import com.mototrip.mapsafety.mapper.OfflineMapTileMapper;
import com.mototrip.mapsafety.service.OfflineMapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OfflineMapServiceImpl implements OfflineMapService {

    private final OfflineMapMapper offlineMapMapper;
    private final OfflineMapTileMapper offlineMapTileMapper;
    private final RocketMQTemplate rocketMQTemplate;

    @Override
    public PageResult<OfflineMap> findAll(int page, int pageSize) {
        LambdaQueryWrapper<OfflineMap> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(OfflineMap::getCreatedAt);
        Page<OfflineMap> pageResult = offlineMapMapper.selectPage(new Page<>(page, pageSize), wrapper);
        return PageResult.of(pageResult.getRecords(), pageResult.getTotal(), page, pageSize);
    }

    @Override
    public OfflineMap findById(Long id) {
        OfflineMap map = offlineMapMapper.selectById(id);
        if (map == null) throw new NotFoundException("离线地图不存在");
        return map;
    }

    @Override
    public OfflineMap create(Long userId, CreateOfflineMapRequest request) {
        OfflineMap map = new OfflineMap();
        map.setName(request.getName());
        map.setDescription(request.getDescription());
        map.setMinLat(request.getMinLat());
        map.setMaxLat(request.getMaxLat());
        map.setMinLng(request.getMinLng());
        map.setMaxLng(request.getMaxLng());
        map.setMinZoom(request.getMinZoom() != null ? request.getMinZoom() : 10);
        map.setMaxZoom(request.getMaxZoom() != null ? request.getMaxZoom() : 18);
        map.setMapProvider(request.getMapProvider() != null ? request.getMapProvider() : "amap");
        map.setTileCount(0);
        map.setDownloadedCount(0);
        map.setStatus("pending");
        map.setDownloadProgress(BigDecimal.ZERO);
        map.setFileSize(0L);
        map.setCreatorId(userId);
        offlineMapMapper.insert(map);

        // 生成瓦片坐标列表
        List<Map<String, Object>> tiles = generateTileCoordinates(map);
        map.setTileCount(tiles.size());
        offlineMapMapper.updateById(map);

        // 批量插入瓦片记录
        for (Map<String, Object> tile : tiles) {
            OfflineMapTile tileEntity = new OfflineMapTile();
            tileEntity.setMapId(map.getId());
            tileEntity.setZ((Integer) tile.get("z"));
            tileEntity.setX((Integer) tile.get("x"));
            tileEntity.setY((Integer) tile.get("y"));
            tileEntity.setStatus("pending");
            offlineMapTileMapper.insert(tileEntity);
        }

        return map;
    }

    @Override
    public void delete(Long userId, Long id) {
        OfflineMap map = offlineMapMapper.selectById(id);
        if (map == null) throw new NotFoundException("离线地图不存在");
        if (!map.getCreatorId().equals(userId)) throw new ForbiddenException("无权删除此地图");
        offlineMapTileMapper.delete(
                new LambdaQueryWrapper<OfflineMapTile>().eq(OfflineMapTile::getMapId, id)
        );
        offlineMapMapper.deleteById(id);
    }

    @Override
    public List<Map<String, Object>> generateTiles(Long id) {
        OfflineMap map = offlineMapMapper.selectById(id);
        if (map == null) throw new NotFoundException("离线地图不存在");
        return generateTileCoordinates(map);
    }

    @Override
    public OfflineMapTile getTile(Long mapId, int z, int x, int y) {
        return offlineMapTileMapper.selectOne(
                new LambdaQueryWrapper<OfflineMapTile>()
                        .eq(OfflineMapTile::getMapId, mapId)
                        .eq(OfflineMapTile::getZ, z)
                        .eq(OfflineMapTile::getX, x)
                        .eq(OfflineMapTile::getY, y)
        );
    }

    @Override
    public void startDownload(Long mapId) {
        OfflineMap map = offlineMapMapper.selectById(mapId);
        if (map == null) throw new NotFoundException("离线地图不存在");
        map.setStatus("downloading");
        map.setDownloadProgress(BigDecimal.ZERO);
        offlineMapMapper.updateById(map);
        // 发送 RocketMQ 消息异步下载
        Map<String, Object> event = new HashMap<>();
        event.put("mapId", mapId);
        event.put("timestamp", System.currentTimeMillis());
        rocketMQTemplate.convertAndSend("MOTOTRIP_MAP_DOWNLOAD", event);
    }

    private List<Map<String, Object>> generateTileCoordinates(OfflineMap map) {
        List<Map<String, Object>> tiles = new ArrayList<>();
        int minZoom = map.getMinZoom() != null ? map.getMinZoom() : 10;
        int maxZoom = map.getMaxZoom() != null ? map.getMaxZoom() : 18;
        for (int z = minZoom; z <= maxZoom; z++) {
            int minX = (int) Math.floor(lonToTileX(map.getMinLng().doubleValue(), z));
            int maxX = (int) Math.floor(lonToTileX(map.getMaxLng().doubleValue(), z));
            int minY = (int) Math.floor(latToTileY(map.getMaxLat().doubleValue(), z));
            int maxY = (int) Math.floor(latToTileY(map.getMinLat().doubleValue(), z));
            for (int x = minX; x <= maxX; x++) {
                for (int y = minY; y <= maxY; y++) {
                    Map<String, Object> tile = new HashMap<>();
                    tile.put("z", z);
                    tile.put("x", x);
                    tile.put("y", y);
                    tiles.add(tile);
                }
            }
        }
        return tiles;
    }

    private double lonToTileX(double lon, int zoom) {
        return (lon + 180.0) / 360.0 * Math.pow(2, zoom);
    }

    private double latToTileY(double lat, int zoom) {
        double latRad = Math.toRadians(lat);
        return (1 - Math.log(Math.tan(latRad) + 1.0 / Math.cos(latRad)) / Math.PI) / 2.0 * Math.pow(2, zoom);
    }
}
