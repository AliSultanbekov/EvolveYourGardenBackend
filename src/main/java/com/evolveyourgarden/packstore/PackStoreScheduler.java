package com.evolveyourgarden.packstore;

import com.evolveyourgarden.shared.RobloxClient;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Component
public class PackStoreScheduler {
    private static final Logger log = LoggerFactory.getLogger(PackStoreScheduler.class);
    private static final long ROTATION_SECONDS = 1800; // 30 minutes

    private final PackStoreService packStoreService;
    private final RobloxClient robloxClient;
    private final TaskScheduler taskScheduler;

    public PackStoreScheduler(
            PackStoreService packStoreService,
            RobloxClient robloxClient,
            TaskScheduler taskScheduler
    ) {
        this.packStoreService = packStoreService;
        this.robloxClient = robloxClient;
        this.taskScheduler = taskScheduler;
    }

    @PostConstruct
    private void start() {
        scheduleNext();
    }

    private void scheduleNext() {
        try {
            packStoreService.refreshStore();
            SaleState currentSale = packStoreService.getCurrentSale();

            robloxClient.publishPackStoreRefreshed(currentSale);
            log.info("Pack store rotated: {}", currentSale.saleId());
        } catch (Exception e) {
            log.error("Pack store rotation failed", e);
        } finally {
            long delayMs = TimeUnit.SECONDS.toMillis(ROTATION_SECONDS);
            taskScheduler.schedule(this::scheduleNext, Instant.now().plusMillis(delayMs));
        }
    }
}
