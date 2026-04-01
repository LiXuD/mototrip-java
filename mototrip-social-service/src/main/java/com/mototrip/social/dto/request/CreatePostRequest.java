package com.mototrip.social.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreatePostRequest {
    @NotBlank(message = "帖子内容不能为空")
    private String content;
    private String images;
    private String location;
}
