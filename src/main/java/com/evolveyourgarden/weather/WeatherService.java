package com.evolveyourgarden.weather;

import com.evolveyourgarden.shared.ChanceUtility;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class WeatherService {
    private volatile WeatherState currentWeather = WeatherState.now("None", 0);

    public void selectWeather() {
        Map<String, Integer> weatherPool = WeatherConfig.getWeatherPool();

        String selectedWeather = ChanceUtility.chooseFromPool(weatherPool);

        int[] durationRange = WeatherConfig.getWeatherDurationRange(selectedWeather);
        int selectedDuration = ChanceUtility.chooseFromRange(durationRange[0], durationRange[1]);

        currentWeather = WeatherState.now(selectedWeather, selectedDuration);
    }

    public WeatherState getCurrentWeather() {
        return currentWeather;
    }
}
