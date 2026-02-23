package com.cleancrafting.evil_removal.mixin;

import com.cleancrafting.evil_removal.BannedContentService;
import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrewingStandBlockEntity.class)
public class BrewingStandBlockEntityMixin {
    @Inject(method = "canCraft", at = @At("HEAD"), cancellable = true)
    private void evilRemoval$disableBrewing(CallbackInfoReturnable<Boolean> cir) {
        if (BannedContentService.isBannedItem(Items.POTION)
            || BannedContentService.isBannedItem(Items.SPLASH_POTION)
            || BannedContentService.isBannedItem(Items.LINGERING_POTION)) {
            cir.setReturnValue(false);
        }
    }
}
