package com.mototrip.common.client;

import com.mototrip.common.client.dto.FootprintStatsDTO;
import com.mototrip.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SocialClientFallbackFactory implements FallbackFactory<SocialClient> {

    @Override
    public SocialClient create(Throwable cause) {
        log.error("SocialClient fallback triggered: {}", cause.getMessage());
        return new SocialClient() {
            @Override
            public Result<FootprintStatsDTO> getFootprintStats(Long userId) {
                log.warn("Fallback: getFootprintsStats({})", userId);
                return Result.error(500, "社交服务暂时不可用");
            }
        };
    }
}
