package com.mototrip.route.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Schema(description = "更新评论请求")
public class UpdateReviewRequest {
    @Schema(description = "评分")
    @Min(value = 1, message = "评分最低1分")
    @Max(value = 5, message = "评分最高5分")
    private Integer rating;
    
    @Schema(description = "评论内容")
    private String content;
}
