package com.mototrip.route.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "创建评论请求")
public class CreateReviewRequest {
    @Schema(description = "路线ID")
    @NotNull(message = "路线ID不能为空")
    private Long routeId;
    
    @Schema(description = "评分")
    @NotNull(message = "评分不能为空")
    @Min(value = 1, message = "评分最低1分")
    @Max(value = 5, message = "评分最高5分")
    private Integer rating;
    
    @Schema(description = "评论内容")
    private String content;
}
