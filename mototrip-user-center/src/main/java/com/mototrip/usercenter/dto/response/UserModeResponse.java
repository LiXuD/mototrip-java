package com.mototrip.usercenter.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserModeResponse {
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
