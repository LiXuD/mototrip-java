package com.mototrip.common.client.dto;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class RouteDTO implements Serializable {
    private Long id;
    private String name;
    private String description;
    private String coverImage;
    private BigDecimal distance;
    private BigDecimal duration;
    private String difficulty;
    private Map<String, Object> startPoint;
    private Map<String, Object> endPoint;
    private Boolean isPublic;
    private Integer likes;
    private Integer views;
    private Boolean isOfficial;
    private BigDecimal avgRating;
    private Integer reviewCount;
    private String seasons;
    private String motorcycleTypes;
    private Long creatorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
