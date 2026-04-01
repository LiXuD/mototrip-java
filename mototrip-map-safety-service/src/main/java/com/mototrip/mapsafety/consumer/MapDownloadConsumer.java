package com.mototrip.mapsafety.consumer;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.mototrip.mapsafety.entity.OfflineMap;
import com.mototrip.mapsafety.entity.OfflineMapTile;
import com.mototrip.mapsafety.mapper.OfflineMapMapper;
import com.mototrip.mapsafety.mapper.OfflineMapTileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Slf4j
@Component
@RocketMQMessageListener(
        topic = "MOTOTRIP_MAP_DOWNLOAD",
        consumerGroup = "mototrip-mapsafety-consumer-group"
)
@RequiredArgsConstructor
public class MapDownloadConsumer implements RocketMQListener<Long> {

    private final OfflineMapMapper offlineMapMapper;
    private final OfflineMapTileMapper offlineMapTileMapper;

    @Override
    public void onMessage(Long mapId) {
        log.info("收到离线地图下载任务, mapId={}", mapId);
        try {
            OfflineMap offlineMap = offlineMapMapper.selectById(mapId);
            if (offlineMap == null) {
                log.warn("离线地图不存在, mapId={}", mapId);
                return;
            }

            // Query all pending tiles for this map
            List<OfflineMapTile> tiles = offlineMapTileMapper.selectList(
                    new LambdaQueryWrapper<OfflineMapTile>()
                            .eq(OfflineMapTile::getMapId, mapId)
                            .eq(OfflineMapTile::getStatus, "pending")
            );

            int totalTiles = tiles.size();
            int downloadedCount = 0;

            for (OfflineMapTile tile : tiles) {
                try {
                    // Simulate tile download (in production, this would download from map provider)
                    // TODO: Implement actual tile download from map provider API
                    String filePath = String.format("/data/maps/tiles/%d/%d/%d/%d.png", mapId, tile.getZ(), tile.getX(), tile.getY());

                    tile.setStatus("downloaded");
                    tile.setFilePath(filePath);
                    tile.setFileSize(1024L); // Simulated file size
                    offlineMapTileMapper.updateById(tile);
                    downloadedCount++;

                    // Update progress every 10 tiles
                    if (downloadedCount % 10 == 0) {
                        BigDecimal progress = new BigDecimal(downloadedCount)
                                .divide(new BigDecimal(totalTiles), 4, RoundingMode.HALF_UP)
                                .multiply(new BigDecimal(100));
                        offlineMapMapper.update(null,
                                new LambdaUpdateWrapper<OfflineMap>()
                                        .eq(OfflineMap::getId, mapId)
                                        .set(OfflineMap::getDownloadedCount, downloadedCount)
                                        .set(OfflineMap::getDownloadProgress, progress)
                        );
                    }
                } catch (Exception e) {
                    log.error("下载瓦片失败: mapId={}, z={}, x={}, y={}", mapId, tile.getZ(), tile.getX(), tile.getY(), e);
                    tile.setStatus("failed");
                    offlineMapTileMapper.updateById(tile);
                }
            }

            // Update final status
            offlineMapMapper.update(null,
                    new LambdaUpdateWrapper<OfflineMap>()
                            .eq(OfflineMap::getId, mapId)
                            .set(OfflineMap::getDownloadedCount, downloadedCount)
                            .set(OfflineMap::getDownloadProgress, new BigDecimal(100))
                            .set(OfflineMap::getStatus, "completed")
            );
            log.info("离线地图下载完成, mapId={}, downloadedCount={}", mapId, downloadedCount);

        } catch (Exception e) {
            log.error("离线地图下载任务处理失败, mapId={}", mapId, e);
            // Mark map as failed
            offlineMapMapper.update(null,
                    new LambdaUpdateWrapper<OfflineMap>()
                            .eq(OfflineMap::getId, mapId)
                            .set(OfflineMap::getStatus, "failed")
            );
        }
    }
}
