package com.mototrip.mapsafety.dto.request;
import lombok.Data;
import java.util.Map;

@Data
public class UpdateNoParkingZoneRequest {
    private String name;
    private String description;
    private Map<String, Object> location;
    private Integer radius;
    private String images;
}
