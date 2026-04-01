package com.mototrip.mapsafety.controller;

import com.mototrip.common.response.Result;
import com.mototrip.mapsafety.dto.response.WarningResponse;
import com.mototrip.mapsafety.service.WarningService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "警告聚合", description = "骑行安全警告聚合")
@RestController
@RequestMapping("/api/warnings")
@RequiredArgsConstructor
public class WarningController {
    private final WarningService warningService;

    @Operation(summary = "获取聚合安全警告")
    @GetMapping
    public Result<WarningResponse> getWarnings(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(defaultValue = "5000") double radius) {
        return Result.success(warningService.getAggregatedWarnings(lat, lng, radius));
    }
}
