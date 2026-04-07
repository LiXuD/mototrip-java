package com.mototrip.social.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "创建评论请求")
public class CreateCommentRequest {
    @Schema(description = "帖子ID")
    @NotNull(message = "帖子ID不能为空")
    private Long postId;
    
    @Schema(description = "评论内容")
    @NotBlank(message = "评论内容不能为空")
    private String content;
    
    @Schema(description = "父评论ID")
    private Long parentId;
}
