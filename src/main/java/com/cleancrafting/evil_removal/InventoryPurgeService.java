package com.cleancrafting.evil_removal;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.HashSet;
import java.util.Set;

public class InventoryPurgeService {
    private static final Set<Identifier> EXTRA_BANNED_ITEMS = new HashSet<>();
    private static final Set<TagKey<Item>> EXTRA_BANNED_TAGS = new HashSet<>();
    private static final String TAG_PREFIX = "tag:";
    private static boolean enabled;
    private static int intervalTicks;
    private static int tickCounter;
    private static MinecraftServer server;

    public static void register(EvilRemovalConfig config) {
        enabled = config.inventoryPurge.enabled;
        intervalTicks = Math.max(20, config.inventoryPurge.purgeIntervalTicks);
        EXTRA_BANNED_ITEMS.clear();
        EXTRA_BANNED_TAGS.clear();
        config.inventoryPurge.bannedItems.forEach(itemId -> {
            if (itemId.startsWith(TAG_PREFIX)) {
                EXTRA_BANNED_TAGS.add(TagKey.of(RegistryKeys.ITEM, new Identifier(itemId.substring(TAG_PREFIX.length()))));
            } else {
                EXTRA_BANNED_ITEMS.add(new Identifier(itemId));
            }
        });

        ServerTickEvents.END_SERVER_TICK.register(server -> tick());
    }

    public static void setServer(MinecraftServer serverInstance) {
        server = serverInstance;
    }

    private static void tick() {
        if (!enabled || server == null) {
            return;
        }
        tickCounter++;
        if (tickCounter < intervalTicks) {
            return;
        }
        tickCounter = 0;

        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            purgeInventory(player);
        }
    }

    private static void purgeInventory(ServerPlayerEntity player) {
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack.isEmpty()) {
                continue;
            }
            Identifier itemId = Registries.ITEM.getId(stack.getItem());
            if (BannedContentService.shouldPurgeItem(stack)
                || EXTRA_BANNED_ITEMS.contains(itemId)
                || isInExtraTag(stack.getItem())) {
                player.getInventory().setStack(i, ItemStack.EMPTY);
            }
        }
    }

    private static boolean isInExtraTag(Item item) {
        for (TagKey<Item> tag : EXTRA_BANNED_TAGS) {
            if (item.getRegistryEntry().isIn(tag)) {
                return true;
            }
        }
        return false;
    }
}
