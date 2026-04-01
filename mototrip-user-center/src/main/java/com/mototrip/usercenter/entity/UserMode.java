package com.mototrip.usercenter.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("mt_user_modes")
public class UserMode {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String mode;
    private Integer maxRideDistance;
    private Integer maxSpeed;
    private Boolean enableDistanceReminder;
    private Boolean enableSpeedReminder;
    private Boolean enableDangerWarning;
    private Boolean enableSimplifiedUI;
    private Boolean enableComfortMode;
    private Boolean enableProfessionalFeatures;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
