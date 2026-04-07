package com.mototrip.trip.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Schema(description = "创建行程请求")
public class CreateTripRequest {
    @Schema(description = "行程名称")
    @NotBlank(message = "行程名称不能为空")
    private String name;
    
    @Schema(description = "行程描述")
    private String description;
    
    @Schema(description = "封面图片")
    private String coverImage;
    
    @Schema(description = "开始日期")
    private LocalDate startDate;
    
    @Schema(description = "结束日期")
    private LocalDate endDate;
    
    @Schema(description = "状态")
    private String status;
    
    @Schema(description = "总距离")
    private BigDecimal totalDistance;
    
    @Schema(description = "备注")
    private String notes;
    
    @Schema(description = "路线ID")
    private Long routeId;
}
