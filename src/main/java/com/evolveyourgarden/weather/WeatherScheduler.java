package com.evolveyourgarden.weather;

import com.evolveyourgarden.shared.RobloxClient;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Component
public class WeatherScheduler {
    private static final Logger log = LoggerFactory.getLogger(WeatherScheduler.class);

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
    private void start() {
        scheduleNext();
    }

    private void scheduleNext() {
        weatherService.selectWeather();
        WeatherState currentWeather = weatherService.getCurrentWeather();

        robloxClient.publishWeather(currentWeather);
        log.info("Weather set to {} for {}s", currentWeather.name(), currentWeather.duration());

        long delayMs = TimeUnit.SECONDS.toMillis(currentWeather.duration());
        taskScheduler.schedule(this::scheduleNext, Instant.now().plusMillis(delayMs));
    }
}
