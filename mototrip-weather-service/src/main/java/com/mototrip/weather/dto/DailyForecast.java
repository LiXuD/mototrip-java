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
public class DailyForecast {
    private String date;
    private double maxTemp;
    private double minTemp;
    private String weatherCode;
    private String weatherDescription;
    private String icon;
    private double precipitationProbability;
    private double maxWindSpeed;
}
