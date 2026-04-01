package com.mototrip.route.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Map;

@Data
public class CreateWaypointRequest {
    @NotBlank(message = "途经点名称不能为空")
    private String name;
    private String description;
    private String type;
    private Map<String, Object> location;
    private String images;
    private BigDecimal rating;
    private String phone;
    private String openingHours;
    private Integer sequence;
    private Map<String, Object> metadata;
    private Long routeId;
    private Long tripId;
}
