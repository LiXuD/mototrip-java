package com.mototrip.route.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Map;

@Data
@Schema(description = "创建途经点请求")
public class CreateWaypointRequest {
    @Schema(description = "途经点名称")
    @NotBlank(message = "途经点名称不能为空")
    private String name;
    
    @Schema(description = "途经点描述")
    private String description;
    
    @Schema(description = "途经点类型")
    private String type;
    
    @Schema(description = "位置")
    private Map<String, Object> location;
    
    @Schema(description = "图片")
    private String images;
    
    @Schema(description = "评分")
    private BigDecimal rating;
    
    @Schema(description = "电话")
    private String phone;
    
    @Schema(description = "营业时间")
    private String openingHours;
    
    @Schema(description = "顺序")
    private Integer sequence;
    
    @Schema(description = "元数据")
    private Map<String, Object> metadata;
    
    @Schema(description = "路线ID")
    private Long routeId;
    
    @Schema(description = "旅行ID")
    private Long tripId;
}
