package com.mototrip.common.client;

import com.mototrip.common.client.dto.WarningDTO;
import com.mototrip.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MapSafetyClientFallbackFactory implements FallbackFactory<MapSafetyClient> {

    @Override
    public MapSafetyClient create(Throwable cause) {
        log.error("MapSafetyClient fallback triggered: {}", cause.getMessage());
        return new MapSafetyClient() {
            @Override
            public Result<WarningDTO> getWarnings(double lat, double lng, double radius) {
                log.warn("Fallback: getWarnings(lat={}, lng={}, radius={})", lat, lng, radius);
                return Result.error(500, "地图安全服务暂时不可用");
            }
        };
    }
}
