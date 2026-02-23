package com.cleancrafting.evil_removal.mixin;

import com.cleancrafting.evil_removal.SpawnControlService;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerEntity.class)
public class VillagerEntityMixin {
    @Inject(method = "onStruckByLightning", at = @At("HEAD"), cancellable = true)
    private void evilRemoval$blockWitchConversion(ServerWorld world, LightningEntity lightning, CallbackInfo ci) {
        if (!SpawnControlService.isSpawnAllowed(EntityType.WITCH, world)) {
            ci.cancel();
        }
    }
}
