package com.mototrip.common.client;

import com.mototrip.common.client.dto.RouteDTO;
import com.mototrip.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RouteClientFallbackFactory implements FallbackFactory<RouteClient> {

    @Override
    public RouteClient create(Throwable cause) {
        log.error("RouteClient fallback triggered: {}", cause.getMessage());
        return new RouteClient() {
            @Override
            public Result<RouteDTO> getRouteById(Long id) {
                log.warn("Fallback: getRouteById({})", id);
                return Result.error(500, "路线服务暂时不可用");
            }
        };
    }
}
