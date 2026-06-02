package com.evolveyourgarden.shared;

import com.evolveyourgarden.packstore.SaleState;
import com.evolveyourgarden.weather.WeatherState;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

@Component
public class RobloxClient {
    private final RestClient restClient;
    private final ObjectMapper mapper = new ObjectMapper();
    private final RobloxProperties properties;

    public RobloxClient(RobloxProperties properties) {
        this.properties = properties;
        this.restClient = RestClient.create();
    }

    public void publishWeather(WeatherState weather) {
        publish("Weather", weather);
    }

    public void publishPackStoreRefreshed(SaleState sale) {
        publish("PackStoreRefreshed", sale);
    }

    private void publish(String topic, Object payload) {
        String url = String.format(
                "https://apis.roblox.com/cloud/v2/universes/%s:publishMessage",
                properties.universeId()
        );

        // The payload's @JsonProperty annotations produce the correct JSON keys.
        String message = mapper.writeValueAsString(payload);

        Map<String, Object> body = Map.of(
                "topic", topic,
                "message", message
        );

        restClient.post()
                .uri(url)
                .header("x-api-key", properties.apiKey())
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .toBodilessEntity();
    }
}
