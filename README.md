# Evil_Removal

Evil_Removal is a Fabric mod that gives full control over hostile mob spawning, entity loot, banned items/blocks, and world purging across the Overworld, Nether, and End. It targets the gameplay changes requested:

- **Mob spawning & loot control:** deny-list mobs per dimension and override entity loot tables (e.g., remove evoker totem drops).
- **Potion removal:** ban potion items, brewing stands, and cauldrons, and disable brewing logic.
- **Enchanting overhaul:** prevent enchanting table use, remove enchanting outputs, and ban enchanted loot items.
- **Soul sand removal:** replace soul sand/soul soil in chunks with configurable replacement blocks.
- **Inventory cleanup:** automatically purge banned items from player inventories.

## Configuration
The first server start generates `config/evil_removal.json` with defaults. Edit to customize your rules.

### Spawn rules
`spawnRules.denyListByDimension` maps a dimension ID to a list of entity IDs to block from natural spawns.

### Loot overrides
`lootRules.entityLootOverrides` maps an entity loot table ID to an override. `ALLOW_ONLY` will create a fresh loot table with just those items. (Use an empty list to drop nothing.)

### Banned content
`bannedContent.items` and `bannedContent.blocks` are treated as globally blocked from usage, crafting, and interaction. Add potions, brewing stands, enchanting tables, soul blocks, etc.

### Block replacement
`blockReplacement.replacements` maps banned block IDs to a replacement block ID. This is used to purge worldgen and structure blocks like soul sand.

### Inventory purge
`inventoryPurge` lets you enable a periodic scan for banned items like carved pumpkins and jack-o-lanterns.

## Installing the mod
1. Build the mod jar with Gradle (`gradle build`).
2. Copy the jar from `build/libs` into your Minecraft instance’s `mods` folder.
3. Launch Minecraft with Fabric Loader for the configured version (1.21.1+) and Fabric API installed.

## Troubleshooting
If Gradle reports `Unsupported class file major version 69`, it means Gradle is running on a newer JDK than it supports. This project is configured for Java 17; ensure the Gradle process uses a Java 17 runtime (for example, by setting `JAVA_HOME` to a JDK 17 install or configuring your IDE’s Gradle JVM).【F:build.gradle†L20-L24】

## Updating for future Minecraft versions
The build uses version properties in `gradle.properties`. Update `minecraft_version`, `yarn_mappings`, `loader_version`, and `fabric_version` there to target new releases without changing the build script.【F:gradle.properties†L1-L7】
