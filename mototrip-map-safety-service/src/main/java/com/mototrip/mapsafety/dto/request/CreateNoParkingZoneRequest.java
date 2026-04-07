package com.mototrip.mapsafety.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.Map;

@Data
@Schema(description = "创建禁停区域请求")
public class CreateNoParkingZoneRequest {
    @Schema(description = "禁停区域名称")
    @NotBlank(message = "禁停区域名称不能为空")
    private String name;
    
    @Schema(description = "禁停区域描述")
    private String description;
    
    @Schema(description = "位置")
    private Map<String, Object> location;
    
    @Schema(description = "半径")
    private Integer radius;
    
    @Schema(description = "图片")
    private String images;
}
