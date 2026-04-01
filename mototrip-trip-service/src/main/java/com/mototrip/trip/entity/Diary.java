package com.mototrip.trip.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@TableName(value = "mt_diaries", autoResultMap = true)
public class Diary {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String content;
    private String images;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> location;
    private String locationName;
    private String weather;
    private String temperature;
    private String mood;
    private Integer likes;
    private Integer comments;
    private String tag;
    private Long userId;
    private Long tripId;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
