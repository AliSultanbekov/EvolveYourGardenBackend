package com.evolveyourgarden.packstore;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Pack(
        @JsonProperty("Id") String id,
        @JsonProperty("Category") String category,
        @JsonProperty("Name") String name,
        @JsonProperty("Stock") int stock,
        @JsonProperty("Left") int left) {}
