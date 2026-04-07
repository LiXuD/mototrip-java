package com.mototrip.social.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Schema(description = "创建足迹请求")
public class CreateFootprintRequest {
    @Schema(description = "纬度")
    @NotNull(message = "纬度不能为空")
    private BigDecimal latitude;
    
    @Schema(description = "经度")
    @NotNull(message = "经度不能为空")
    private BigDecimal longitude;
    
    @Schema(description = "位置名称")
    private String locationName;
    
    @Schema(description = "省份")
    private String province;
    
    @Schema(description = "城市")
    private String city;
    
    @Schema(description = "区县")
    private String district;
    
    @Schema(description = "图片")
    private String image;
    
    @Schema(description = "备注")
    private String note;
    
    @Schema(description = "距离")
    private BigDecimal distance;
}
