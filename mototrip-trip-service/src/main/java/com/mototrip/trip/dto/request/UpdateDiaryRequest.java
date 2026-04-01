package com.mototrip.trip.dto.request;
import lombok.Data;
import java.util.Map;

@Data
public class UpdateDiaryRequest {
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
