package com.mototrip.mapsafety.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.Map;

@Data
@Schema(description = "创建危险区域请求")
public class CreateDangerZoneRequest {
    @Schema(description = "危险区域名称")
    @NotBlank(message = "危险区域名称不能为空")
    private String name;
    
    @Schema(description = "危险区域描述")
    private String description;
    
    @Schema(description = "位置")
    private Map<String, Object> location;
    
    @Schema(description = "半径")
    private Integer radius;
    
    @Schema(description = "类型")
    private String type;
    
    @Schema(description = "严重程度")
    private String severity;
    
    @Schema(description = "图片")
    private String images;
}
