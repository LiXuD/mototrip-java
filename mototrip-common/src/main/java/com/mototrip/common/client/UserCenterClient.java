package com.mototrip.common.client;

import com.mototrip.common.client.dto.PublicUserDTO;
import com.mototrip.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "mototrip-user-center", path = "/api/users", fallbackFactory = UserCenterClientFallbackFactory.class)
public interface UserCenterClient {

    @GetMapping("/{id}")
    Result<PublicUserDTO> getUserById(@PathVariable("id") Long id);

    @GetMapping("/batch")
    Result<List<PublicUserDTO>> getUsersByIds(@RequestParam("ids") List<Long> ids);
}
