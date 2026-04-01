package com.mototrip.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteWeatherResponse {
    private List<WaypointWeather> waypoints;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WaypointWeather {
        private int index;
        private String name;
        private double latitude;
        private double longitude;
        private WeatherResponse weather;
    }
}
