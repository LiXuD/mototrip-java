package com.mototrip.mapsafety.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Schema(description = "更新位置请求")
public class UpdateLocationRequest {
    @Schema(description = "纬度")
    @NotNull(message = "纬度不能为空")
    private BigDecimal latitude;
    
    @Schema(description = "经度")
    @NotNull(message = "经度不能为空")
    private BigDecimal longitude;
    
    @Schema(description = "速度")
    private BigDecimal speed;
    
    @Schema(description = "航向")
    private BigDecimal heading;
}
