package com.mototrip.mapsafety.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("mt_location_shares")
public class LocationShare {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String shareCode;
    private Boolean isActive;
    private LocalDateTime expiresAt;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
