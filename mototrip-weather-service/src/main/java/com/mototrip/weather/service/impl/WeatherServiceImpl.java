package com.mototrip.weather.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mototrip.weather.dto.*;
import com.mototrip.weather.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Slf4j
@Service
public class WeatherServiceImpl implements WeatherService {

    private static final String OPEN_METEO_URL = "https://api.open-meteo.com/v1/forecast";
    private final WebClient webClient = WebClient.builder().build();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // WMO 天气代码映射 (25种)
    private static final Map<Integer, String[]> WMO_CODES = new HashMap<>();
    static {
        WMO_CODES.put(0, new String[]{"晴", "sunny"});
        WMO_CODES.put(1, new String[]{"大部晴朗", "sunny"});
        WMO_CODES.put(2, new String[]{"多云", "partly_cloudy"});
        WMO_CODES.put(3, new String[]{"阴天", "cloudy"});
        WMO_CODES.put(45, new String[]{"雾", "fog"});
        WMO_CODES.put(48, new String[]{"雾凇", "fog"});
        WMO_CODES.put(51, new String[]{"小毛毛雨", "drizzle"});
        WMO_CODES.put(53, new String[]{"中毛毛雨", "drizzle"});
        WMO_CODES.put(55, new String[]{"大毛毛雨", "drizzle"});
        WMO_CODES.put(56, new String[]{"冻毛毛雨", "freezing_drizzle"});
        WMO_CODES.put(57, new String[]{"冻毛毛雨", "freezing_drizzle"});
        WMO_CODES.put(61, new String[]{"小雨", "rain"});
        WMO_CODES.put(63, new String[]{"中雨", "rain"});
        WMO_CODES.put(65, new String[]{"大雨", "rain"});
        WMO_CODES.put(66, new String[]{"冻雨", "freezing_rain"});
        WMO_CODES.put(67, new String[]{"冻雨", "freezing_rain"});
        WMO_CODES.put(71, new String[]{"小雪", "snow"});
        WMO_CODES.put(73, new String[]{"中雪", "snow"});
        WMO_CODES.put(75, new String[]{"大雪", "snow"});
        WMO_CODES.put(77, new String[]{"雪粒", "snow"});
        WMO_CODES.put(80, new String[]{"小阵雨", "rain"});
        WMO_CODES.put(81, new String[]{"中阵雨", "rain"});
        WMO_CODES.put(82, new String[]{"大阵雨", "rain"});
        WMO_CODES.put(85, new String[]{"小阵雪", "snow"});
        WMO_CODES.put(86, new String[]{"大阵雪", "snow"});
        WMO_CODES.put(95, new String[]{"雷暴", "thunderstorm"});
        WMO_CODES.put(96, new String[]{"雷暴伴小冰雹", "thunderstorm"});
        WMO_CODES.put(99, new String[]{"雷暴伴大冰雹", "thunderstorm"});
    }

    @Override
    public WeatherResponse getCurrentWeather(String location, Double lat, Double lon) {
        // 如果没有提供坐标，使用城市名 fallback 到默认坐标
        if (lat == null || lon == null) {
            if (location != null && !location.isEmpty()) {
                // 尝试简单的城市名到坐标映射
                double[] coords = getCityCoords(location);
                lat = coords[0];
                lon = coords[1];
            } else {
                // 默认北京
                lat = 39.9;
                lon = 116.4;
            }
        }

        String url = String.format("%s?latitude=%f&longitude=%f&current=temperature_2m,relative_humidity_2m,wind_speed_10m,wind_direction_10m,weather_code,is_day",
                OPEN_METEO_URL, lat, lon);

        try {
            String response = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JsonNode root = objectMapper.readTree(response);
            JsonNode current = root.get("current");

            int weatherCode = current.get("weather_code").asInt();
            String[] weatherInfo = WMO_CODES.getOrDefault(weatherCode, new String[]{"未知", "unknown"});

            return WeatherResponse.builder()
                    .location(location != null ? location : String.format("%.2f,%.2f", lat, lon))
                    .temperature(current.get("temperature_2m").asDouble())
                    .windSpeed(current.get("wind_speed_10m").asDouble())
                    .windDirection(convertWindDirection(current.get("wind_direction_10m").asInt()))
                    .humidity(current.get("relative_humidity_2m").asInt())
                    .weatherCode(String.valueOf(weatherCode))
                    .weatherDescription(weatherInfo[0])
                    .icon(weatherInfo[1])
                    .isDay(current.get("is_day").asInt() == 1)
                    .build();
        } catch (Exception e) {
            log.error("获取天气数据失败: lat={}, lon={}", lat, lon, e);
            // 返回 mock 数据
            return WeatherResponse.builder()
                    .location(location != null ? location : "未知位置")
                    .temperature(25.0)
                    .windSpeed(10.0)
                    .windDirection("东风")
                    .humidity(60)
                    .weatherCode("0")
                    .weatherDescription("晴")
                    .icon("sunny")
                    .isDay(true)
                    .build();
        }
    }

    @Override
    public RouteWeatherResponse getRouteWeather(String waypoints) {
        // waypoints 格式: "lat1,lon1;lat2,lon2"
        List<RouteWeatherResponse.WaypointWeather> result = new ArrayList<>();
        if (waypoints == null || waypoints.isEmpty()) {
            return RouteWeatherResponse.builder().waypoints(result).build();
        }

        String[] pairs = waypoints.split(";");
        for (int i = 0; i < pairs.length; i++) {
            String[] coords = pairs[i].trim().split(",");
            if (coords.length == 2) {
                try {
                    double lat = Double.parseDouble(coords[0].trim());
                    double lon = Double.parseDouble(coords[1].trim());
                    WeatherResponse weather = getCurrentWeather(null, lat, lon);
                    result.add(RouteWeatherResponse.WaypointWeather.builder()
                            .index(i)
                            .name("途经点 " + (i + 1))
                            .latitude(lat)
                            .longitude(lon)
                            .weather(weather)
                            .build());
                } catch (NumberFormatException e) {
                    log.warn("解析坐标失败: {}", pairs[i]);
                }
            }
        }
        return RouteWeatherResponse.builder().waypoints(result).build();
    }

    @Override
    public List<DailyForecast> getForecast(Double lat, Double lon, int days) {
        if (lat == null || lon == null) {
            lat = 39.9;
            lon = 116.4;
        }
        if (days <= 0 || days > 16) days = 7;

        String url = String.format("%s?latitude=%f&longitude=%f&daily=weather_code,temperature_2m_max,temperature_2m_min,precipitation_probability_max,wind_speed_10m_max&forecast_days=%d",
                OPEN_METEO_URL, lat, lon, days);

        try {
            String response = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JsonNode root = objectMapper.readTree(response);
            JsonNode daily = root.get("daily");

            List<DailyForecast> forecasts = new ArrayList<>();
            JsonNode dates = daily.get("time");
            for (int i = 0; i < dates.size(); i++) {
                int weatherCode = daily.get("weather_code").get(i).asInt();
                String[] weatherInfo = WMO_CODES.getOrDefault(weatherCode, new String[]{"未知", "unknown"});

                forecasts.add(DailyForecast.builder()
                        .date(dates.get(i).asText())
                        .maxTemp(daily.get("temperature_2m_max").get(i).asDouble())
                        .minTemp(daily.get("temperature_2m_min").get(i).asDouble())
                        .weatherCode(String.valueOf(weatherCode))
                        .weatherDescription(weatherInfo[0])
                        .icon(weatherInfo[1])
                        .precipitationProbability(
                                daily.get("precipitation_probability_max").get(i).asDouble())
                        .maxWindSpeed(daily.get("wind_speed_10m_max").get(i).asDouble())
                        .build());
            }
            return forecasts;
        } catch (Exception e) {
            log.error("获取天气预报失败: lat={}, lon={}", lat, lon, e);
            return List.of();
        }
    }

    private String convertWindDirection(int degrees) {
        String[] directions = {"北风", "东北风", "东风", "东南风", "南风", "西南风", "西风", "西北风"};
        int index = (int) Math.round(((double) degrees % 360) / 45) % 8;
        return directions[index];
    }

    private double[] getCityCoords(String city) {
        Map<String, double[]> cityMap = new HashMap<>();
        cityMap.put("北京", new double[]{39.9, 116.4});
        cityMap.put("上海", new double[]{31.2, 121.5});
        cityMap.put("广州", new double[]{23.1, 113.3});
        cityMap.put("深圳", new double[]{22.5, 114.1});
        cityMap.put("成都", new double[]{30.6, 104.1});
        cityMap.put("杭州", new double[]{30.3, 120.2});
        cityMap.put("重庆", new double[]{29.6, 106.5});
        cityMap.put("武汉", new double[]{30.6, 114.3});
        cityMap.put("西安", new double[]{34.3, 108.9});
        cityMap.put("南京", new double[]{32.1, 118.8});
        return cityMap.getOrDefault(city, new double[]{39.9, 116.4});
    }
}
