package com.mototrip.trip.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.Map;

@Data
public class CreateDiaryRequest {
    @NotBlank(message = "日记标题不能为空")
    private String title;
    private String content;
    private String images;
    private Map<String, Object> location;
    private String locationName;
    private String weather;
    private String temperature;
    private String mood;
    private String tag;
    private Long tripId;
}
