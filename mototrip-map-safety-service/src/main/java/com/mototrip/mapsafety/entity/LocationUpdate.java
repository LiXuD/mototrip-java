package com.mototrip.mapsafety.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("mt_location_updates")
public class LocationUpdate {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long shareId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal speed;
    private BigDecimal heading;
    private LocalDateTime recordedAt;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
