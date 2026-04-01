package com.mototrip.common.client;

import com.mototrip.common.client.dto.DailyForecastDTO;
import com.mototrip.common.client.dto.RouteWeatherDTO;
import com.mototrip.common.client.dto.WeatherDTO;
import com.mototrip.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "mototrip-weather-service", path = "/api/weather", fallbackFactory = WeatherClientFallbackFactory.class)
public interface WeatherClient {

    @GetMapping("/current")
    Result<WeatherDTO> getCurrentWeather(
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "lat", required = false) Double lat,
            @RequestParam(value = "lon", required = false) Double lon);

    @GetMapping("/route")
    Result<RouteWeatherDTO> getRouteWeather(@RequestParam("waypoints") String waypoints);

    @GetMapping("/forecast")
    Result<List<DailyForecastDTO>> getForecast(
            @RequestParam(value = "lat", required = false) Double lat,
            @RequestParam(value = "lon", required = false) Double lon,
            @RequestParam(value = "days", defaultValue = "7") int days);
}
