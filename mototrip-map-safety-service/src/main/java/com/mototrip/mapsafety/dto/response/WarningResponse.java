package com.mototrip.mapsafety.dto.response;

import com.mototrip.mapsafety.entity.DangerZone;
import com.mototrip.mapsafety.entity.NoParkingZone;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class WarningResponse {
    private Map<String, Object> nightWarning;
    private List<DangerZone> dangerZones;
    private List<NoParkingZone> noParkingZones;
}
