package com.mototrip.common.client;

import com.mototrip.common.client.dto.WarningDTO;
import com.mototrip.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "mototrip-map-safety-service", path = "/api", fallbackFactory = MapSafetyClientFallbackFactory.class)
public interface MapSafetyClient {

    @GetMapping("/warnings")
    Result<WarningDTO> getWarnings(
            @RequestParam("lat") double lat,
            @RequestParam("lng") double lng,
            @RequestParam(value = "radius", defaultValue = "5000") double radius);
}
