package com.mototrip.mapsafety.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.Map;

@Data
public class CreateNoParkingZoneRequest {
    @NotBlank(message = "禁停区域名称不能为空")
    private String name;
    private String description;
    private Map<String, Object> location;
    private Integer radius;
    private String images;
}
