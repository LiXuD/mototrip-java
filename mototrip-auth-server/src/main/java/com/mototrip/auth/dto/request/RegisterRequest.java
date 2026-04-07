package com.mototrip.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "注册请求")
public class RegisterRequest {
    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    @Size(min = 8, message = "密码长度至少8位")
    private String password;

    @Schema(description = "昵称")
    @NotBlank(message = "昵称不能为空")
    private String nickname;
}
