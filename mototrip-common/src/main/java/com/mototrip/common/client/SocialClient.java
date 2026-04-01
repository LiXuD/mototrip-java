package com.mototrip.common.client;

import com.mototrip.common.client.dto.FootprintStatsDTO;
import com.mototrip.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "mototrip-social-service", path = "/api", fallbackFactory = SocialClientFallbackFactory.class)
public interface SocialClient {

    @GetMapping("/footprints/stats")
    Result<FootprintStatsDTO> getFootprintStats(@RequestParam("userId") Long userId);
}
