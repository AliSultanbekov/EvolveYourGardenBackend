package com.evolveyourgarden.weather;

import java.util.Map;

public final class WeatherConfig {
    private static final Map<String, Integer> WEATHER_POOL = Map.of(
            "None", 50,
            "Rainy", 50
    );
    private static final Map<String, int[]> WEATHER_DURATIONS = Map.of(
            "Rainy", new int[]{30, 60},
            "None", new int[]{30, 60}
    );

    private WeatherConfig() {
    }

    public static Map<String, Integer> getWeatherPool() {
        return WEATHER_POOL;
    }

    public static int[] getWeatherDurationRange(String weatherName) {
        return WEATHER_DURATIONS.get(weatherName);
    }
}
