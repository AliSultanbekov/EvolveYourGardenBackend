package com.evolveyourgarden.packstore;

import java.util.Map;

public final class PackStoreConfig {
    public record PackDef(int weight, int minStock, int maxStock) {}
    public record Category(int slotCount, Map<String, PackDef> packs) {}

    private static final Map<String, Category> CATEGORIES = Map.of(
            "Special", new Category(3, Map.of(
                    "Super Pack", new PackDef(50, 1, 2),
                    "Mega Pack",  new PackDef(50, 1, 2)
            ))
    );

    private PackStoreConfig() {
    }

    public static Map<String, Category> getCategories() {
        return CATEGORIES;
    }
}
