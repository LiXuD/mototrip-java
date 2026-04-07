package com.mototrip.team.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "创建车队请求")
public class CreateTeamRequest {
    @Schema(description = "车队名称")
    @NotBlank(message = "车队名称不能为空")
    private String name;
    
    @Schema(description = "车队描述")
    private String description;
    
    @Schema(description = "目的地")
    private String destination;
    
    @Schema(description = "开始时间")
    private LocalDateTime startTime;
    
    @Schema(description = "结束时间")
    private LocalDateTime endTime;
    
    @Schema(description = "最大成员数")
    private Integer maxMembers;
    
    @Schema(description = "封面图片")
    private String coverImage;
    
    @Schema(description = "纬度")
    private BigDecimal latitude;
    
    @Schema(description = "经度")
    private BigDecimal longitude;
}
