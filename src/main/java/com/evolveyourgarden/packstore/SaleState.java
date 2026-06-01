package com.evolveyourgarden.packstore;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record SaleState(
        @JsonProperty("SaleId") String saleId,
        @JsonProperty("Packs") List<Pack> packs,
        @JsonProperty("StartTime") long startTime) {

    public SaleState {
        packs = List.copyOf(packs);   // keep the defensive copy
    }

    // factory stamps "now" — callers don't pass startTime
    public static SaleState now(String saleId, List<Pack> packs) {
        return new SaleState(saleId, packs, System.currentTimeMillis() / 1000L);
    }
}