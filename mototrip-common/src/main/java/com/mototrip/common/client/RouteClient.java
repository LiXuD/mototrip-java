package com.mototrip.common.client;

import com.mototrip.common.client.dto.RouteDTO;
import com.mototrip.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "mototrip-route-service", path = "/api/routes", fallbackFactory = RouteClientFallbackFactory.class)
public interface RouteClient {

    @GetMapping("/{id}")
    Result<RouteDTO> getRouteById(@PathVariable("id") Long id);
}
