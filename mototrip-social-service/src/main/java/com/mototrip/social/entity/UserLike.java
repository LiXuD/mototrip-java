package com.mototrip.social.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("mt_user_likes")
public class UserLike {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String targetType;
    private Long targetId;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
