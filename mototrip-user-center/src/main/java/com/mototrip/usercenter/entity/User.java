package com.mototrip.usercenter.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("mt_users")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    @TableField(select = false)
    private String password;
    private String nickname;
    private String avatar;
    @TableField(select = false)
    private String phone;
    @TableField(select = false)
    private String email;
    private String bio;
    private String motorcycle;
    private String ridingExperience;
    private String motorcycleType;
    @TableField(select = false)
    private String openid;
    private Integer followersCount;
    private Integer followingCount;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
