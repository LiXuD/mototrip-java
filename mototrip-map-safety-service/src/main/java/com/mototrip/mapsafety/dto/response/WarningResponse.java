package com.mototrip.mapsafety.dto.response;

import com.mototrip.mapsafety.entity.DangerZone;
import com.mototrip.mapsafety.entity.NoParkingZone;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Schema(description = "警告响应")
public class WarningResponse {
    @Schema(description = "夜间警告")
    private Map<String, Object> nightWarning;
    
    @Schema(description = "危险区域列表")
    private List<DangerZone> dangerZones;
    
    @Schema(description = "禁停区域列表")
    private List<NoParkingZone> noParkingZones;
}
