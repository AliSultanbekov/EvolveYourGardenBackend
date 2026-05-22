package com.evolveyourgarden.weather_service;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherState {
    private String name;
    private int duration;

    WeatherState(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public void SetWeather(String name, int Duration) {
        this.name = name;
        this.duration = Duration;
    }

    @JsonProperty("WeatherName")
    public String GetName() {
        return this.name;
    }

    @JsonProperty("WeatherDuration")
    public int GetDuration() {
        return this.duration;
    }
}
