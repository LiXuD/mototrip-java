package com.mototrip.weather.controller;

import com.mototrip.common.response.Result;
import com.mototrip.weather.dto.DailyForecast;
import com.mototrip.weather.dto.RouteWeatherResponse;
import com.mototrip.weather.dto.WeatherResponse;
import com.mototrip.weather.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "天气服务", description = "天气查询 API 代理")
@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @Operation(summary = "获取当前天气")
    @GetMapping("/current")
    public Result<WeatherResponse> getCurrentWeather(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lon) {
        return Result.success(weatherService.getCurrentWeather(location, lat, lon));
    }

    @Operation(summary = "获取沿途天气")
    @GetMapping("/route")
    public Result<RouteWeatherResponse> getRouteWeather(
            @RequestParam String waypoints) {
        return Result.success(weatherService.getRouteWeather(waypoints));
    }

    @Operation(summary = "获取天气预报")
    @GetMapping("/forecast")
    public Result<List<DailyForecast>> getForecast(
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lon,
            @RequestParam(defaultValue = "7") int days) {
        return Result.success(weatherService.getForecast(lat, lon, days));
    }
}
