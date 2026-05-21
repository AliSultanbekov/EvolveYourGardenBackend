package com.evolveyourgarden.weather_service;

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

    public String GetName() {
        return this.name;
    }

    public int GetDuration() {
        return this.duration;
    }
}
