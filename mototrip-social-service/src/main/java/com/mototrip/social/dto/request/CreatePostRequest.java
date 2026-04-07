package com.mototrip.social.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "创建帖子请求")
public class CreatePostRequest {
    @Schema(description = "帖子内容")
    @NotBlank(message = "帖子内容不能为空")
    private String content;
    
    @Schema(description = "图片")
    private String images;
    
    @Schema(description = "位置")
    private String location;
}
