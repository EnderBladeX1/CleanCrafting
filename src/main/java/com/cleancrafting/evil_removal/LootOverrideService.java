package com.cleancrafting.evil_removal;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;

public class LootOverrideService {
    public static void register(EvilRemovalConfig config) {
        LootTableEvents.REPLACE.register((resourceManager, lootManager, id, table, source) -> {
            EvilRemovalConfig.LootOverride override = config.lootRules.entityLootOverrides.get(id.toString());
            if (override == null) {
                return null;
            }

            if (override.mode == EvilRemovalConfig.LootMode.ALLOW_ONLY) {
                return buildAllowOnlyTable(override.items);
            }

            return LootTable.builder().build();
        });
    }

    private static LootTable buildAllowOnlyTable(List<String> itemIds) {
        LootPool.Builder pool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1));
        for (String itemId : itemIds) {
            Item item = Registries.ITEM.get(new Identifier(itemId));
            pool.with(ItemEntry.builder(item));
        }
        return LootTable.builder().pool(pool).build();
    }
}
