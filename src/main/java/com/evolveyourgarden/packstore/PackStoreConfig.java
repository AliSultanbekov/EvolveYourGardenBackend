package com.evolveyourgarden.packstore;

import java.util.Map;

public final class PackStoreConfig {
    public record PackDef(int weight, int minStock, int maxStock) {}
    public record Category(int slotCount, Map<String, PackDef> packs) {}

    private static final Map<String, Category> CATEGORIES = Map.of(
            "Special", new Category(3, Map.of(
                    "Super Pack", new PackDef(50, 1, 2),
                    "Mega Pack",  new PackDef(50, 1, 2)
            )),
            "Event", new Category(3, Map.of(
                    "Elite Pack",     new PackDef(30, 1, 1),
                    "Legendary Pack", new PackDef(10, 1, 1)
            ))
    );

    private PackStoreConfig() {
    }

    public static Map<String, Category> getCategories() {
        return CATEGORIES;
    }

    public static Category getPool(String categoryName) {
        return CATEGORIES.get(categoryName);
    }

    public static PackDef getPack(String categoryName, String packName) {
        return CATEGORIES.get(categoryName).packs().get(packName);
    }
}
