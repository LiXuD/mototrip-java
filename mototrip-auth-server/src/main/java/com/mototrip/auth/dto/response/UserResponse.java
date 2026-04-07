package com.mototrip.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "用户信息响应")
public class UserResponse {
    @Schema(description = "用户ID")
    private Long id;
    
    @Schema(description = "用户名")
    private String username;
    
    @Schema(description = "昵称")
    private String nickname;
    
    @Schema(description = "头像")
    private String avatar;
    
    @Schema(description = "个人简介")
    private String bio;
    
    @Schema(description = "摩托车")
    private String motorcycle;
    
    @Schema(description = "骑行经验")
    private String ridingExperience;
    
    @Schema(description = "摩托车类型")
    private String motorcycleType;
    
    @Schema(description = "关注数")
    private Integer followersCount;
    
    @Schema(description = "粉丝数")
    private Integer followingCount;
    
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
}
