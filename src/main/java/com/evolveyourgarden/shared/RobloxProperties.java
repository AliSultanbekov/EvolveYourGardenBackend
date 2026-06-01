package com.evolveyourgarden.shared;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "roblox")
public record RobloxProperties(String apiKey, String universeId) {
}
