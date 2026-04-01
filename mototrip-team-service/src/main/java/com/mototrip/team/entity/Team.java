package com.mototrip.team.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("mt_teams")
public class Team {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String destination;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer maxMembers;
    private String status;
    private String coverImage;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Long creatorId;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
