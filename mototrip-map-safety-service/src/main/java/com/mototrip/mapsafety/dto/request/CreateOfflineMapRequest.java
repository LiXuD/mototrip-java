package com.mototrip.mapsafety.dto.request;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateOfflineMapRequest {
    @NotNull(message = "地图名称不能为空")
    private String name;
    private String description;
    @NotNull(message = "边界不能为空")
    private BigDecimal minLat;
    private BigDecimal maxLat;
    private BigDecimal minLng;
    private BigDecimal maxLng;
    private Integer minZoom;
    private Integer maxZoom;
    private String mapProvider;
}
