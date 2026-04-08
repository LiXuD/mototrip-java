package com.mototrip.weather.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "路线天气响应")
public class RouteWeatherResponse {
    @Schema(description = "途经点天气列表")
    private List<WaypointWeather> waypoints;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "途经点天气")
    public static class WaypointWeather {
        @Schema(description = "途经点索引")
        private int index;
        
        @Schema(description = "途经点名称")
        private String name;
        
        @Schema(description = "纬度")
        private double latitude;
        
        @Schema(description = "经度")
        private double longitude;
        
        @Schema(description = "天气信息")
        private WeatherResponse weather;
    }
}
