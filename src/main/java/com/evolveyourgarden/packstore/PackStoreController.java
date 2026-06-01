package com.evolveyourgarden.packstore;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PackStoreController {
    private final PackStoreService packStoreService;

    public PackStoreController(PackStoreService packStoreService) {
        this.packStoreService = packStoreService;
    }

    @GetMapping("/packstore/current-sale")
    public SaleState getCurrentSale() {
        return packStoreService.getCurrentSale();
    }
}
