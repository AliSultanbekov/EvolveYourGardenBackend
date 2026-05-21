package com.evolveyourgarden.weather_service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.TaskScheduler;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Component
public class WeatherScheduler {
    private final WeatherService weatherService;
    private final RobloxClient robloxClient;
    private final TaskScheduler taskScheduler;

    public WeatherScheduler(
            WeatherService weatherService,
            RobloxClient robloxClient,
            TaskScheduler taskScheduler
    ) {
        this.weatherService = weatherService;
        this.robloxClient = robloxClient;
        this.taskScheduler = taskScheduler;
    }

    @PostConstruct
    private void Start() {
        ScheduleNext();
    }

    private void ScheduleNext() {
        weatherService.SelectWeather();
        WeatherState currentWeather = weatherService.GetCurrentWeather();

        System.out.println(currentWeather.GetName());

        long delayMs = TimeUnit.SECONDS.toMillis(currentWeather.GetDuration());
        taskScheduler.schedule(this::ScheduleNext, Instant.now().plusMillis(delayMs));
    }
}
