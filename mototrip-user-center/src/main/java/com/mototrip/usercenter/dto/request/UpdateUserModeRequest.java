package com.mototrip.usercenter.dto.request;

import lombok.Data;

@Data
public class UpdateUserModeRequest {
    private String mode;
    private Integer maxRideDistance;
    private Integer maxSpeed;
    private Boolean enableDistanceReminder;
    private Boolean enableSpeedReminder;
    private Boolean enableDangerWarning;
    private Boolean enableSimplifiedUI;
    private Boolean enableComfortMode;
    private Boolean enableProfessionalFeatures;
}
