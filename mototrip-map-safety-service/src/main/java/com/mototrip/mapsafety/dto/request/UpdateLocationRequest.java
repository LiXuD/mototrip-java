package com.mototrip.mapsafety.dto.request;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class UpdateLocationRequest {
    @NotNull(message = "纬度不能为空")
    private BigDecimal latitude;
    @NotNull(message = "经度不能为空")
    private BigDecimal longitude;
    private BigDecimal speed;
    private BigDecimal heading;
}
