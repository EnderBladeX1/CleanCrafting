package com.cleancrafting.evil_removal;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

public class BannedContentService {
    private static final Set<Identifier> BANNED_ITEMS = new HashSet<>();
    private static final Set<Identifier> BANNED_BLOCKS = new HashSet<>();
    private static final Set<TagKey<Item>> BANNED_ITEM_TAGS = new HashSet<>();
    private static final String TAG_PREFIX = "tag:";

    public static void register(EvilRemovalConfig config) {
        BANNED_ITEMS.clear();
        BANNED_BLOCKS.clear();
        BANNED_ITEM_TAGS.clear();
        config.bannedContent.items.forEach(itemId -> {
            if (itemId.startsWith(TAG_PREFIX)) {
                BANNED_ITEM_TAGS.add(TagKey.of(RegistryKeys.ITEM, new Identifier(itemId.substring(TAG_PREFIX.length()))));
            } else {
                BANNED_ITEMS.add(new Identifier(itemId));
            }
        });
        config.bannedContent.blocks.stream().map(Identifier::new).forEach(BANNED_BLOCKS::add);

        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack stack = player.getStackInHand(hand);
            if (isBannedItem(stack.getItem())) {
                return TypedActionResult.fail(stack);
            }
            return TypedActionResult.pass(stack);
        });

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            BlockState state = world.getBlockState(hitResult.getBlockPos());
            if (isBannedBlock(state.getBlock())) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });

        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> {
            if (isBannedBlock(state.getBlock())) {
                return false;
            }
            return true;
        });
    }

    public static boolean isBannedItem(Item item) {
        Identifier id = Registries.ITEM.getId(item);
        if (BANNED_ITEMS.contains(id)) {
            return true;
        }
        for (TagKey<Item> tag : BANNED_ITEM_TAGS) {
            if (item.getRegistryEntry().isIn(tag)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBannedBlock(Block block) {
        Identifier id = Registries.BLOCK.getId(block);
        return BANNED_BLOCKS.contains(id);
    }

    public static boolean shouldPurgeItem(ItemStack stack) {
        return isBannedItem(stack.getItem());
    }
}
