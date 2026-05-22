package com.evolveyourgarden.weather_service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

@Component
public class RobloxClient {
    private final RestClient restClient;
    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${ROBLOX_API_KEY}")
    private String apiKey;

    @Value("${ROBLOX_UNIVERSE_ID}")
    private String universeId;

    public RobloxClient() {
        this.restClient = RestClient.create();
    }

    public void PublishWeather(WeatherState weather) {
        String url = String.format(
                "https://apis.roblox.com/cloud/v2/universes/%s:publishMessage",
                universeId
        );

        String message = mapper.writeValueAsString(Map.of(
                "WeatherName", weather.GetName(),
                "WeatherDuration", weather.GetDuration(),
                "WeatherStartTime", weather.GetStartTime()
        ));

        Map<String, Object> body = Map.of(
                "topic", "Weather",
                "message", message
        );

        restClient.post()
                .uri(url)
                .header("x-api-key", apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .toBodilessEntity();
    }
}
