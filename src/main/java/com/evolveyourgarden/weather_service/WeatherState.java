package com.evolveyourgarden.weather_service;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherState {
    private String name;
    private int duration;
    private long startTime;

    WeatherState(String name, int duration) {
        this.name = name;
        this.duration = duration;
        this.startTime = System.currentTimeMillis() / 1000L;
    }

    public void SetWeather(String name, int Duration) {
        this.name = name;
        this.duration = Duration;
        this.startTime = System.currentTimeMillis() / 1000L;
    }

    @JsonProperty("Name")
    public String GetName() {
        return this.name;
    }

    @JsonProperty("Duration")
    public int GetDuration() {
        return this.duration;
    }

    @JsonProperty("StartTime")
    public long GetStartTime() {
        return this.startTime;
    }
}
