package com.mototrip.usercenter.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "用户模式响应")
public class UserModeResponse {
    @Schema(description = "ID")
    private Long id;
    
    @Schema(description = "用户ID")
    private Long userId;
    
    @Schema(description = "用户模式")
    private String mode;
    
    @Schema(description = "最大骑行距离")
    private Integer maxRideDistance;
    
    @Schema(description = "最大速度")
    private Integer maxSpeed;
    
    @Schema(description = "启用距离提醒")
    private Boolean enableDistanceReminder;
    
    @Schema(description = "启用速度提醒")
    private Boolean enableSpeedReminder;
    
    @Schema(description = "启用危险警告")
    private Boolean enableDangerWarning;
    
    @Schema(description = "启用简化界面")
    private Boolean enableSimplifiedUI;
    
    @Schema(description = "启用舒适模式")
    private Boolean enableComfortMode;
    
    @Schema(description = "启用专业功能")
    private Boolean enableProfessionalFeatures;
    
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
    
    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
}
