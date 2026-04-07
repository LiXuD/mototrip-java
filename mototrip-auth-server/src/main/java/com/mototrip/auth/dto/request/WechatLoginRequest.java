package com.mototrip.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "微信登录请求")
public class WechatLoginRequest {
    @Schema(description = "微信登录code")
    @NotBlank(message = "code不能为空")
    private String code;
}
