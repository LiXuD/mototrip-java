package com.mototrip.usercenter.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "更新用户模式请求")
public class UpdateUserModeRequest {
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
}
