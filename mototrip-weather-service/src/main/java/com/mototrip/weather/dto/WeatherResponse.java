package com.mototrip.weather.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "天气响应")
public class WeatherResponse {
    @Schema(description = "位置")
    private String location;
    
    @Schema(description = "温度")
    private double temperature;
    
    @Schema(description = "风速")
    private double windSpeed;
    
    @Schema(description = "风向")
    private String windDirection;
    
    @Schema(description = "湿度")
    private int humidity;
    
    @Schema(description = "天气代码")
    private String weatherCode;
    
    @Schema(description = "天气描述")
    private String weatherDescription;
    
    @Schema(description = "天气图标")
    private String icon;
    
    @Schema(description = "是否白天")
    private boolean isDay;
}
