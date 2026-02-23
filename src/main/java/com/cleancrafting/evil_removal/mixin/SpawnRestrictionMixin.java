package com.cleancrafting.evil_removal.mixin;

import com.cleancrafting.evil_removal.SpawnControlService;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.world.ServerWorldAccess;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.SpawnRestriction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpawnRestriction.class)
public class SpawnRestrictionMixin {
    @Inject(method = "canSpawn", at = @At("HEAD"), cancellable = true)
    private static <T> void evilRemoval$canSpawn(EntityType<T> type, ServerWorldAccess world, SpawnReason spawnReason,
                                                 BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir) {
        if (!SpawnControlService.isSpawnAllowed(type, world)) {
            cir.setReturnValue(false);
        }
    }
}
