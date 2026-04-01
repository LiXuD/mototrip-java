package com.mototrip.social.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("mt_footprints")
public class Footprint {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String locationName;
    private String province;
    private String city;
    private String district;
    private String image;
    private String note;
    private Integer visitCount;
    private BigDecimal distance;
    private LocalDateTime visitedAt;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
