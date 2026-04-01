package com.mototrip.mapsafety.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("mt_offline_maps")
public class OfflineMap {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String description;
    private BigDecimal minLat;
    private BigDecimal maxLat;
    private BigDecimal minLng;
    private BigDecimal maxLng;
    private Integer minZoom;
    private Integer maxZoom;
    private String mapProvider;
    private Integer tileCount;
    private Integer downloadedCount;
    private String status;
    private BigDecimal downloadProgress;
    private String filePath;
    private Long fileSize;
    private Long creatorId;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
