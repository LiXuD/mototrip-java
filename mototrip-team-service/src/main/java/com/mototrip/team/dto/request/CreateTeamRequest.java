package com.mototrip.team.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CreateTeamRequest {
    @NotBlank(message = "车队名称不能为空")
    private String name;
    private String description;
    private String destination;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer maxMembers;
    private String coverImage;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
