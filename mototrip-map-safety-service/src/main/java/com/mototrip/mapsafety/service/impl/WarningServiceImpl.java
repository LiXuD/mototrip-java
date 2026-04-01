package com.mototrip.mapsafety.service.impl;

import com.mototrip.mapsafety.dto.response.WarningResponse;
import com.mototrip.mapsafety.entity.DangerZone;
import com.mototrip.mapsafety.entity.NoParkingZone;
import com.mototrip.mapsafety.mapper.DangerZoneMapper;
import com.mototrip.mapsafety.mapper.NoParkingZoneMapper;
import com.mototrip.mapsafety.service.DangerZoneService;
import com.mototrip.mapsafety.service.NoParkingZoneService;
import com.mototrip.mapsafety.service.WarningService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WarningServiceImpl implements WarningService {

    private final DangerZoneService dangerZoneService;
    private final NoParkingZoneService noParkingZoneService;

    @Override
    public WarningResponse getAggregatedWarnings(double lat, double lng, double radius) {
        WarningResponse response = new WarningResponse();

        // 夜间警告
        Map<String, Object> nightWarning = new HashMap<>();
        boolean isNight = LocalTime.now().isAfter(LocalTime.of(18, 0))
                || LocalTime.now().isBefore(LocalTime.of(6, 0));
        nightWarning.put("isNight", isNight);
        nightWarning.put("message", isNight ? "当前为夜间骑行时段，请注意安全" : "当前为日间骑行时段");
        response.setNightWarning(nightWarning);

        // 附近危险区域
        response.setDangerZones(dangerZoneService.findNearby(lat, lng, radius));

        // 附近禁停区域
        response.setNoParkingZones(noParkingZoneService.findNearby(lat, lng, radius));

        return response;
    }
}
