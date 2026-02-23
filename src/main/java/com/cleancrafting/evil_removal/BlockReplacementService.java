package com.cleancrafting.evil_removal;

import net.fabricmc.fabric.api.world.event.lifecycle.v1.ServerChunkEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.WorldChunk;

import java.util.HashMap;
import java.util.Map;

public class BlockReplacementService {
    private static final Map<Block, Block> REPLACEMENTS = new HashMap<>();
    public static void register(EvilRemovalConfig config) {
        REPLACEMENTS.clear();
        config.blockReplacement.replacements.forEach((fromId, toId) -> {
            Block fromBlock = Registries.BLOCK.get(new Identifier(fromId));
            Block toBlock = Registries.BLOCK.get(new Identifier(toId));
            REPLACEMENTS.put(fromBlock, toBlock);
        });

        ServerChunkEvents.CHUNK_LOAD.register((world, chunk) -> {
            if (!REPLACEMENTS.isEmpty()) {
                purgeChunk(world, chunk);
            }
        });
    }

    private static void purgeChunk(ServerWorld world, WorldChunk chunk) {
        int startX = chunk.getPos().getStartX();
        int startZ = chunk.getPos().getStartZ();
        int endY = world.getTopY();
        int startY = world.getBottomY();
        BlockPos.Mutable pos = new BlockPos.Mutable();

        for (int y = startY; y < endY; y++) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    pos.set(startX + x, y, startZ + z);
                    BlockState state = chunk.getBlockState(pos);
                    Block replacement = REPLACEMENTS.get(state.getBlock());
                    if (replacement != null) {
                        world.setBlockState(pos, replacement.getDefaultState(), Block.NOTIFY_LISTENERS);
                    }
                }
            }
        }
    }
}
