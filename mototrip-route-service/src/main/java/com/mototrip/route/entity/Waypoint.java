package com.mototrip.route.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@TableName(value = "mt_waypoints", autoResultMap = true)
public class Waypoint {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String type;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> location;
    private String images;
    private BigDecimal rating;
    private String phone;
    private String openingHours;
    private Integer sequence;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> metadata;
    private Long routeId;
    private Long tripId;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
