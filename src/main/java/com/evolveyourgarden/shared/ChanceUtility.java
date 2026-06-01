package com.evolveyourgarden.shared;

import java.util.Map;
import java.util.Random;

public final class ChanceUtility {
    private static final Random rng = new Random();

    private ChanceUtility() {
    }

    public static String chooseFromPool(Map<String, Integer> pool) {
        int totalChance = 0;

        for (Map.Entry<String, Integer> entry : pool.entrySet()) {
            totalChance += entry.getValue();
        }

        double randomNumber = rng.nextDouble() * totalChance;
        double accum = 0.0;

        for (Map.Entry<String, Integer> entry : pool.entrySet()) {
            accum += entry.getValue();

            if (randomNumber < accum) {
                return entry.getKey();
            }
        }

        throw new IllegalStateException("Pool is empty or weights do not sum correctly");
    }

    public static int chooseFromRange(int min, int max) {
        return rng.nextInt(min, max + 1);
    }
}
