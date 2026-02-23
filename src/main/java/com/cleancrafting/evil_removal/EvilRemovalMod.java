package com.cleancrafting.evil_removal;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

public class EvilRemovalMod implements ModInitializer {
    public static final String MOD_ID = "evil_removal";
    private static EvilRemovalConfig config;

    @Override
    public void onInitialize() {
        config = ConfigManager.load();

        LootOverrideService.register(config);
        InventoryPurgeService.register(config);
        BlockReplacementService.register(config);
        BannedContentService.register(config);

        ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStarted);
    }

    private void onServerStarted(MinecraftServer server) {
        InventoryPurgeService.setServer(server);
    }

    public static EvilRemovalConfig getConfig() {
        return config;
    }
}
