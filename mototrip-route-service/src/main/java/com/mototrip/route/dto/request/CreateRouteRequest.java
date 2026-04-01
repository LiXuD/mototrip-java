package com.mototrip.route.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Map;

@Data
public class CreateRouteRequest {
    @NotBlank(message = "路线名称不能为空")
    private String name;
    private String description;
    private String coverImage;
    private BigDecimal distance;
    private BigDecimal duration;
    private String difficulty;
    private Map<String, Object> startPoint;
    private Map<String, Object> endPoint;
    private Boolean isPublic;
    private String seasons;
    private String motorcycleTypes;
}
