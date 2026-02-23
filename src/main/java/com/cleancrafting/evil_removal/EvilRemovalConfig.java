package com.cleancrafting.evil_removal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvilRemovalConfig {
    public SpawnRules spawnRules = new SpawnRules();
    public LootRules lootRules = new LootRules();
    public BannedContent bannedContent = new BannedContent();
    public BlockReplacement blockReplacement = new BlockReplacement();
    public InventoryPurge inventoryPurge = new InventoryPurge();

    public static EvilRemovalConfig createDefault() {
        EvilRemovalConfig config = new EvilRemovalConfig();
        List<String> blockedMobs = List.of(
            "minecraft:evoker",
            "minecraft:zombie",
            "minecraft:zombie_nautilus",
            "minecraft:zombie_villager",
            "minecraft:zombie_horse",
            "minecraft:husk",
            "minecraft:drowned",
            "minecraft:zombified_piglin",
            "minecraft:zoglin",
            "minecraft:skeleton",
            "minecraft:stray",
            "minecraft:wither_skeleton",
            "minecraft:skeleton_horse",
            "minecraft:witch",
            "minecraft:ghast",
            "minecraft:phantom",
            "minecraft:vex"
        );
        config.spawnRules.denyListByDimension.put("minecraft:overworld", blockedMobs);
        config.spawnRules.denyListByDimension.put("minecraft:the_nether", blockedMobs);
        config.spawnRules.denyListByDimension.put("minecraft:the_end", blockedMobs);

        config.lootRules.entityLootOverrides.put("minecraft:entities/evoker", LootOverride.allowOnly(List.of()));
        config.lootRules.entityLootOverrides.put("minecraft:entities/zombie", LootOverride.allowOnly(List.of()));
        config.lootRules.entityLootOverrides.put("minecraft:entities/zombie_villager", LootOverride.allowOnly(List.of()));
        config.lootRules.entityLootOverrides.put("minecraft:entities/zombie_horse", LootOverride.allowOnly(List.of()));
        config.lootRules.entityLootOverrides.put("minecraft:entities/husk", LootOverride.allowOnly(List.of()));
        config.lootRules.entityLootOverrides.put("minecraft:entities/drowned", LootOverride.allowOnly(List.of()));
        config.lootRules.entityLootOverrides.put("minecraft:entities/zombified_piglin", LootOverride.allowOnly(List.of()));
        config.lootRules.entityLootOverrides.put("minecraft:entities/zoglin", LootOverride.allowOnly(List.of()));
        config.lootRules.entityLootOverrides.put("minecraft:entities/skeleton", LootOverride.allowOnly(List.of()));
        config.lootRules.entityLootOverrides.put("minecraft:entities/stray", LootOverride.allowOnly(List.of()));
        config.lootRules.entityLootOverrides.put("minecraft:entities/wither_skeleton", LootOverride.allowOnly(List.of()));
        config.lootRules.entityLootOverrides.put("minecraft:entities/skeleton_horse", LootOverride.allowOnly(List.of()));
        config.lootRules.entityLootOverrides.put("minecraft:entities/witch", LootOverride.allowOnly(List.of()));
        config.lootRules.entityLootOverrides.put("minecraft:entities/ghast", LootOverride.allowOnly(List.of()));
        config.lootRules.entityLootOverrides.put("minecraft:entities/phantom", LootOverride.allowOnly(List.of()));
        config.lootRules.entityLootOverrides.put("minecraft:entities/vex", LootOverride.allowOnly(List.of()));

        config.bannedContent.items.addAll(List.of(
            "minecraft:potion",
            "minecraft:splash_potion",
            "minecraft:lingering_potion",
            "minecraft:dragon_breath",
            "minecraft:brewing_stand",
            "minecraft:cauldron",
            "minecraft:enchanting_table",
            "minecraft:enchanted_book",
            "minecraft:enchanted_golden_apple",
            "minecraft:rotten_flesh",
            "minecraft:totem_of_undying",
            "tag:minecraft:potion",
            "tag:minecraft:potions",
            "minecraft:soul_sand",
            "minecraft:soul_soil",
            "minecraft:soul_torch",
            "minecraft:soul_lantern",
            "minecraft:soul_campfire",
            "minecraft:carved_pumpkin",
            "minecraft:jack_o_lantern"
        ));

        config.bannedContent.blocks.addAll(List.of(
            "minecraft:brewing_stand",
            "minecraft:cauldron",
            "minecraft:enchanting_table",
            "minecraft:soul_sand",
            "minecraft:soul_soil",
            "minecraft:soul_torch",
            "minecraft:soul_lantern",
            "minecraft:soul_campfire"
        ));

        config.blockReplacement.replacements.put("minecraft:soul_sand", "minecraft:netherrack");
        config.blockReplacement.replacements.put("minecraft:soul_soil", "minecraft:netherrack");

        config.inventoryPurge.enabled = true;
        config.inventoryPurge.bannedItems.addAll(List.of(
            "minecraft:carved_pumpkin",
            "minecraft:jack_o_lantern",
            "minecraft:enchanted_book",
            "minecraft:enchanted_golden_apple",
            "minecraft:rotten_flesh",
            "minecraft:totem_of_undying",
            "tag:minecraft:potion",
            "tag:minecraft:potions"
        ));
        return config;
    }

    public static class SpawnRules {
        public Map<String, List<String>> denyListByDimension = new HashMap<>();
    }

    public static class LootRules {
        public Map<String, LootOverride> entityLootOverrides = new HashMap<>();
    }

    public static class LootOverride {
        public LootMode mode = LootMode.ALLOW_ONLY;
        public List<String> items = new ArrayList<>();

        public static LootOverride allowOnly(List<String> items) {
            LootOverride override = new LootOverride();
            override.mode = LootMode.ALLOW_ONLY;
            override.items.addAll(items);
            return override;
        }
    }

    public enum LootMode {
        ALLOW_ONLY,
        DENY_ITEMS
    }

    public static class BannedContent {
        public List<String> items = new ArrayList<>();
        public List<String> blocks = new ArrayList<>();
    }

    public static class BlockReplacement {
        public Map<String, String> replacements = new HashMap<>();
    }

    public static class InventoryPurge {
        public boolean enabled = false;
        public int purgeIntervalTicks = 100;
        public List<String> bannedItems = new ArrayList<>();
    }
}
