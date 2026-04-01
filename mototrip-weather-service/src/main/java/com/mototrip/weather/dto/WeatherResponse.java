package com.mototrip.weather.dto;

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
public class WeatherResponse {
    private String location;
    private double temperature;
    private double windSpeed;
    private String windDirection;
    private int humidity;
    private String weatherCode;
    private String weatherDescription;
    private String icon;
    private boolean isDay;
}
