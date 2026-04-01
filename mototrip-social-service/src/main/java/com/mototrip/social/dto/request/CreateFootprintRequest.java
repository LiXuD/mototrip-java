package com.mototrip.social.dto.request;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateFootprintRequest {
    @NotNull(message = "纬度不能为空")
    private BigDecimal latitude;
    @NotNull(message = "经度不能为空")
    private BigDecimal longitude;
    private String locationName;
    private String province;
    private String city;
    private String district;
    private String image;
    private String note;
    private BigDecimal distance;
}
