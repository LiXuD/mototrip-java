package com.mototrip.common.client.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class DailyForecastDTO implements Serializable {
    private String date;
    private double maxTemp;
    private double minTemp;
    private String weatherCode;
    private String weatherDescription;
    private String icon;
    private double precipitationProbability;
    private double maxWindSpeed;
}
