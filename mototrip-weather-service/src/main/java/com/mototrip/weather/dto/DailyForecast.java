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
@Schema(description = "每日天气预报")
public class DailyForecast {
    @Schema(description = "日期")
    private String date;
    
    @Schema(description = "最高温度")
    private double maxTemp;
    
    @Schema(description = "最低温度")
    private double minTemp;
    
    @Schema(description = "天气代码")
    private String weatherCode;
    
    @Schema(description = "天气描述")
    private String weatherDescription;
    
    @Schema(description = "天气图标")
    private String icon;
    
    @Schema(description = "降水概率")
    private double precipitationProbability;
    
    @Schema(description = "最大风速")
    private double maxWindSpeed;
}
