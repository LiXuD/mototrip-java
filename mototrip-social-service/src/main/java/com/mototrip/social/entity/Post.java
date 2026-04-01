package com.mototrip.social.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("mt_posts")
public class Post {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String content;
    private String images;
    private String location;
    private Integer likes;
    private Integer comments;
    private Integer shares;
    private Long userId;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
