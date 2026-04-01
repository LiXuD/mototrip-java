package com.mototrip.common.client.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class RouteWeatherDTO implements Serializable {
    private List<WaypointWeatherDTO> waypoints;

    @Data
    public static class WaypointWeatherDTO implements Serializable {
        private int index;
        private String name;
        private double latitude;
        private double longitude;
        private WeatherDTO weather;
    }
}
