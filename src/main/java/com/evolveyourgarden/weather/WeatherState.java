package com.evolveyourgarden.weather;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WeatherState(
        @JsonProperty("Name") String name,
        @JsonProperty("Duration") int duration,
        @JsonProperty("StartTime") long startTime
) {
    public static WeatherState now(String name, int duration) {
        return new WeatherState(name, duration, System.currentTimeMillis() / 1000L);
    }
}