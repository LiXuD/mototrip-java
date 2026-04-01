package com.mototrip.mapsafety.dto.request;
import lombok.Data;
import java.util.Map;

@Data
public class UpdateDangerZoneRequest {
    private String name;
    private String description;
    private Map<String, Object> location;
    private Integer radius;
    private String type;
    private String severity;
    private String images;
}
