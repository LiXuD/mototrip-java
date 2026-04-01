package com.mototrip.common.client.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class WeatherDTO implements Serializable {
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
