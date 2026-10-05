# Evolve Your Garden — Backend

A Java/Spring Boot service that owns the **shared, cross-server state** for my multiplayer game *Evolve Your Garden*.

The game runs as many separate server instances that can't see each other. Some state has to be identical for every player on every server at the same moment, such as the current weather and the rotating shop sale. This service is the single source of truth for that state: it decides it, serves it over HTTP, and pushes every change to all live game servers.

## What it manages

| Feature | Behaviour |
|---|---|
| **Weather** | Picks the next weather with a weighted random choice and a duration within that weather's range. When the duration ends, it picks again. |
| **Pack store** | Every 30 minutes builds a new sale: for each shop category it picks packs by weight and rolls each one's stock within a min/max range. Each sale gets a unique ID. |

## How game servers use it

```
           ┌──────────────────────────┐
           │  Spring Boot backend     │
           │  (single source of truth)│
           └──────────┬───────────────┘
        HTTP GET      │      push on every change
   (on server start)  │  (Roblox Open Cloud Messaging)
                      ▼
   ┌─────────────┐ ┌─────────────┐ ┌─────────────┐
   │ game server │ │ game server │ │ game server │  ...
   └─────────────┘ └─────────────┘ └─────────────┘
```

1. **On start-up**, a game server fetches the current state over HTTP so it isn't empty while it waits for the next change.
2. **On every change**, the backend publishes the new state to a message topic that every live server subscribes to (`Weather`, `PackStoreRefreshed`).
3. Each state carries a **start time** (and the sale an **ID**), so servers can tell when their copy is stale and refresh players who joined mid-sale.

## API

All endpoints require the header `x-api-key`; requests without the right key get `401`.

| Method | Path | Returns |
|---|---|---|
| `GET` | `/weather` | `{ "Name", "Duration", "StartTime" }` |
| `GET` | `/packstore/current-sale` | `{ "SaleId", "Packs": [ { "Id", "Category", "Name", "Stock", "Left" } ], "StartTime" }` |

## Design notes

- **Immutable snapshots.** Each state is a Java `record` replaced as a whole and held in a `volatile` field, so request threads always read a complete, consistent snapshot while the scheduler thread swaps in the next one.
- **Self-rescheduling tasks.** Each rotation schedules the next one on Spring's `TaskScheduler`, so weather can use variable durations.
- **Small, separate features.** `weather/` and `packstore/` each have their own config, service, scheduler and controller; shared code (Roblox client, API-key filter, weighted-choice utility) lives in `shared/`.

## Running it

Requirements: Java 21. Spring Boot 4.

Set these before starting (as environment variables or in `application.properties`):

| Setting | Purpose |
|---|---|
| `BACKEND_API_KEY` | Key that game servers must send in `x-api-key` |
| `roblox.api-key` | Roblox Open Cloud API key used to publish messages |
| `roblox.universe-id` | ID of the game to publish to |

```bash
./mvnw spring-boot:run
```

## Known limitations and next steps

This was built to prove the game loop first, not for scale. Things I'd change:

- **State is in memory on one instance.** A restart loses the current sale, and two instances would disagree. Next step: persist the current state in a database and let only one instance run the rotation.