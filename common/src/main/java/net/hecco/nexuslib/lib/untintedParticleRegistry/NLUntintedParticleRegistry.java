package net.hecco.nexuslib.lib.untintedParticleRegistry;

import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class NLUntintedParticleRegistry {
    private static final List<Block> BLOCKS = new ArrayList<>();

    public static void add(Block block) {
        BLOCKS.add(block);
    }

    public static List<Block> getBlocks() {
        return BLOCKS;
    }
}
