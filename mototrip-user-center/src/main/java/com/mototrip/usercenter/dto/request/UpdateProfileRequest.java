package com.mototrip.usercenter.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "更新个人资料请求")
public class UpdateProfileRequest {
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
}
