package com.cleancrafting.evil_removal.mixin;

import com.cleancrafting.evil_removal.BannedContentService;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {
    @Shadow
    @Final
    private Map<RecipeType<?>, Map<Identifier, Recipe<?>>> recipes;

    @Inject(method = "apply", at = @At("TAIL"))
    private void evilRemoval$filterRecipes(Map<Identifier, com.google.gson.JsonElement> map, ResourceManager resourceManager,
                                           Profiler profiler, CallbackInfo ci) {
        for (Map<Identifier, Recipe<?>> typeMap : recipes.values()) {
            typeMap.entrySet().removeIf(entry -> {
                Recipe<?> recipe = entry.getValue();
                return BannedContentService.isBannedItem(recipe.getOutput(RegistryWrapper.WrapperLookup.EMPTY).getItem());
            });
        }
    }
}
