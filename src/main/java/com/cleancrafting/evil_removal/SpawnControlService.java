package com.cleancrafting.evil_removal;

import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.world.ServerWorldAccess;
import net.minecraft.util.Identifier;

import java.util.List;

public class SpawnControlService {
    public static boolean isSpawnAllowed(EntityType<?> entityType, ServerWorldAccess world) {
        EvilRemovalConfig config = EvilRemovalMod.getConfig();
        if (config == null) {
            return true;
        }
        Identifier dimensionId = world.getRegistryKey().getValue();
        List<String> denyList = config.spawnRules.denyListByDimension.get(dimensionId.toString());
        if (denyList == null || denyList.isEmpty()) {
            return true;
        }
        Identifier entityId = EntityType.getId(entityType);
        return !denyList.contains(entityId.toString());
    }
}
