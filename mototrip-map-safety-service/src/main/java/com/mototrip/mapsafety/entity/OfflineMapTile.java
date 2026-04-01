package com.mototrip.mapsafety.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("mt_offline_map_tiles")
public class OfflineMapTile {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long mapId;
    private Integer z;
    private Integer x;
    private Integer y;
    private String status;
    private String filePath;
    private Long fileSize;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
