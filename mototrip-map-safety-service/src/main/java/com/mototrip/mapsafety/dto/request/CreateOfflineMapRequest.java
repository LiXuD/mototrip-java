package com.mototrip.mapsafety.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Schema(description = "创建离线地图请求")
public class CreateOfflineMapRequest {
    @Schema(description = "地图名称")
    @NotNull(message = "地图名称不能为空")
    private String name;
    
    @Schema(description = "地图描述")
    private String description;
    
    @Schema(description = "最小纬度")
    @NotNull(message = "边界不能为空")
    private BigDecimal minLat;
    
    @Schema(description = "最大纬度")
    private BigDecimal maxLat;
    
    @Schema(description = "最小经度")
    private BigDecimal minLng;
    
    @Schema(description = "最大经度")
    private BigDecimal maxLng;
    
    @Schema(description = "最小缩放级别")
    private Integer minZoom;
    
    @Schema(description = "最大缩放级别")
    private Integer maxZoom;
    
    @Schema(description = "地图提供商")
    private String mapProvider;
}
