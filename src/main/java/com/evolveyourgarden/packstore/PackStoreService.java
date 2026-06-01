package com.evolveyourgarden.packstore;

import com.evolveyourgarden.shared.ChanceUtility;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class PackStoreService {
    private volatile SaleState currentSale = SaleState.now("", List.of());

    public void refreshStore() {
        String saleId = UUID.randomUUID().toString();
        List<Pack> packs = new ArrayList<>();

        for (Map.Entry<String, PackStoreConfig.Category> categoryEntry : PackStoreConfig.getCategories().entrySet()) {
            String categoryName = categoryEntry.getKey();
            PackStoreConfig.Category category = categoryEntry.getValue();

            Map<String, Integer> weights = new HashMap<>();
            for (Map.Entry<String, PackStoreConfig.PackDef> packEntry : category.packs().entrySet()) {
                weights.put(packEntry.getKey(), packEntry.getValue().weight());
            }

            for (int i = 0; i < category.slotCount(); i++) {
                String packName = ChanceUtility.chooseFromPool(weights);
                PackStoreConfig.PackDef def = category.packs().get(packName);
                int stock = ChanceUtility.chooseFromRange(def.minStock(), def.maxStock());
                String packId = UUID.randomUUID().toString();

                packs.add(new Pack(packId, categoryName, packName, stock, stock));
            }
        }

        this.currentSale = SaleState.now(saleId, packs);
    }

    public SaleState getCurrentSale() {
        return this.currentSale;
    }
}
