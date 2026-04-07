package com.mototrip.route.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Map;

@Data
@Schema(description = "更新路线请求")
public class UpdateRouteRequest {
    @Schema(description = "路线名称")
    private String name;
    
    @Schema(description = "路线描述")
    private String description;
    
    @Schema(description = "封面图片")
    private String coverImage;
    
    @Schema(description = "距离")
    private BigDecimal distance;
    
    @Schema(description = "时长")
    private BigDecimal duration;
    
    @Schema(description = "难度")
    private String difficulty;
    
    @Schema(description = "起点")
    private Map<String, Object> startPoint;
    
    @Schema(description = "终点")
    private Map<String, Object> endPoint;
    
    @Schema(description = "是否公开")
    private Boolean isPublic;
    
    @Schema(description = "季节")
    private String seasons;
    
    @Schema(description = "适用摩托车类型")
    private String motorcycleTypes;
}
