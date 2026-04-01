package com.mototrip.route.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@TableName(value = "mt_routes", autoResultMap = true)
public class Route {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String coverImage;
    private BigDecimal distance;
    private BigDecimal duration;
    private String difficulty;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> startPoint;
    @TableField(typeHandler = JacksonTypeHandler.class)
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
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
