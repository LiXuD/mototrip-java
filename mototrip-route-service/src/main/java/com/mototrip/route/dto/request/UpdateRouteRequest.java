package com.mototrip.route.dto.request;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Map;

@Data
public class UpdateRouteRequest {
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
