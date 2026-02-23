package com.cleancrafting.evil_removal.mixin;

import com.cleancrafting.evil_removal.BannedContentService;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.Items;
import net.minecraft.screen.EnchantmentScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(EnchantmentScreenHandler.class)
public class EnchantmentScreenHandlerMixin {
    @Inject(method = "generateEnchantments", at = @At("HEAD"), cancellable = true)
    private void evilRemoval$disableEnchanting(CallbackInfoReturnable<List<EnchantmentLevelEntry>> cir) {
        if (BannedContentService.isBannedItem(Items.ENCHANTING_TABLE)
            || BannedContentService.isBannedItem(Items.ENCHANTED_BOOK)) {
            cir.setReturnValue(List.of());
        }
    }
}
