package com.mototrip.common.client;

import com.mototrip.common.client.dto.DailyForecastDTO;
import com.mototrip.common.client.dto.RouteWeatherDTO;
import com.mototrip.common.client.dto.WeatherDTO;
import com.mototrip.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class WeatherClientFallbackFactory implements FallbackFactory<WeatherClient> {

    @Override
    public WeatherClient create(Throwable cause) {
        log.error("WeatherClient fallback triggered: {}", cause.getMessage());
        return new WeatherClient() {
            @Override
            public Result<WeatherDTO> getCurrentWeather(String location, Double lat, Double lon) {
                log.warn("Fallback: getCurrentWeather(location={}, lat={}, lon={})", location, lat, lon);
                return Result.error(500, "天气服务暂时不可用");
            }

            @Override
            public Result<RouteWeatherDTO> getRouteWeather(String waypoints) {
                log.warn("Fallback: getRouteWeather(waypoints={})", waypoints);
                return Result.error(500, "天气服务暂时不可用");
            }

            @Override
            public Result<List<DailyForecastDTO>> getForecast(Double lat, Double lon, int days) {
                log.warn("Fallback: getForecast(lat={}, lon={}, days={})", lat, lon, days);
                return Result.error(500, "天气服务暂时不可用");
            }
        };
    }
}
