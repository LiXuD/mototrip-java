package com.mototrip.mapsafety.entity;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@TableName(value = "mt_no_parking_zones", autoResultMap = true)
public class NoParkingZone {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String description;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> location;
    private Integer radius;
    private String images;
    private Long reporterId;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
