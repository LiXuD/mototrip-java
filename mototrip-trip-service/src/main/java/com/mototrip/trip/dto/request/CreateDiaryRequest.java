package com.mototrip.trip.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.Map;

@Data
@Schema(description = "创建日记请求")
public class CreateDiaryRequest {
    @Schema(description = "日记标题")
    @NotBlank(message = "日记标题不能为空")
    private String title;
    
    @Schema(description = "日记内容")
    private String content;
    
    @Schema(description = "图片")
    private String images;
    
    @Schema(description = "位置")
    private Map<String, Object> location;
    
    @Schema(description = "位置名称")
    private String locationName;
    
    @Schema(description = "天气")
    private String weather;
    
    @Schema(description = "温度")
    private String temperature;
    
    @Schema(description = "心情")
    private String mood;
    
    @Schema(description = "标签")
    private String tag;
    
    @Schema(description = "旅行ID")
    private Long tripId;
}
