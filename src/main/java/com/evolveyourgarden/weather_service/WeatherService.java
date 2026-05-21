package com.evolveyourgarden.weather_service;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;

@Component
public class WeatherService {
    private final Random rng = new Random();
    private final WeatherState currentWeather = new WeatherState("", 0);

    public void SelectWeather() {
        Map<String, Integer> weatherPool = WeatherConfig.GetWeatherPool();
        int totalChance = 0;

        for (Map.Entry<String, Integer> entry : weatherPool.entrySet()) {
            totalChance += entry.getValue();
        }

        double randomNumber = rng.nextDouble() * totalChance;
        double accum = 0.0;

        String selectedWeather = "";
        int selectedDuration;

        for (Map.Entry<String, Integer> entry : weatherPool.entrySet()) {
            accum += entry.getValue();
            
            if (randomNumber < accum) {
                selectedWeather = entry.getKey();
            }
        }

        if (selectedWeather.isEmpty()) {
            throw new IllegalStateException("Weather pool exhausted — weights may not sum correctly");
        }

        int[] durationRange = WeatherConfig.GetWeatherDurationRange(selectedWeather);
        selectedDuration =  rng.nextInt(durationRange[0], durationRange[1] + 1);

        currentWeather.SetWeather(selectedWeather, selectedDuration);
    }

    public WeatherState GetCurrentWeather() {
        return currentWeather;
    }
}
