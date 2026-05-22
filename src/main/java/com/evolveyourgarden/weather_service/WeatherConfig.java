package com.evolveyourgarden.weather_service;

import java.util.Map;

public class WeatherConfig {
    public static final Map<String, Integer> weatherPool = Map.of(
            "None", 50,
            "Rainy", 50
    );
    public static final Map<String, int[]> weatherDurations = Map.of(
            "Rainy", new int[]{30, 60},
            "None", new int[]{30, 60}
    );

    public static Map<String, Integer> GetWeatherPool() {
        return weatherPool;
    }

    public static int[] GetWeatherDurationRange(String weatherName) {
        return weatherDurations.get(weatherName);
    }
}
