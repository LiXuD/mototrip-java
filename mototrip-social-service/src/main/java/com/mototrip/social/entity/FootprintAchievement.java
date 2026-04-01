package com.mototrip.social.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("mt_footprint_achievements")
public class FootprintAchievement {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String type;
    private Long userId;
    private String name;
    private String description;
    private String icon;
    private Integer targetCount;
    private Integer currentCount;
    private Boolean unlocked;
    private LocalDateTime unlockedAt;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
