package com.mototrip.trip.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@TableName(value = "mt_trip_shares", autoResultMap = true)
public class TripShare {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long tripId;
    private String title;
    private String summary;
    private BigDecimal totalDistance;
    private BigDecimal duration;
    private Integer waypointCount;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Map<String, Object>> routePoints;
    private Boolean isShared;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
