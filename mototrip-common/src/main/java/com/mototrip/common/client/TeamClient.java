package com.mototrip.common.client;

import com.mototrip.common.client.dto.TeamDTO;
import com.mototrip.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "mototrip-team-service", path = "/api/teams", fallbackFactory = TeamClientFallbackFactory.class)
public interface TeamClient {

    @GetMapping("/{id}")
    Result<TeamDTO> getTeamById(@PathVariable("id") Long id);
}
