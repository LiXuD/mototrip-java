package com.mototrip.weather.service;

import com.mototrip.weather.dto.DailyForecast;
import com.mototrip.weather.dto.RouteWeatherResponse;
import com.mototrip.weather.dto.WeatherResponse;

import java.util.List;

public interface WeatherService {
    WeatherResponse getCurrentWeather(String location, Double lat, Double lon);
    RouteWeatherResponse getRouteWeather(String waypoints);
    List<DailyForecast> getForecast(Double lat, Double lon, int days);
}
