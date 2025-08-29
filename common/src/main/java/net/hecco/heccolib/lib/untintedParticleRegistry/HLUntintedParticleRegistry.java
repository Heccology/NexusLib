package net.hecco.heccolib.lib.untintedParticleRegistry;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HLUntintedParticleRegistry {
    private static final List<Block> BLOCKS = new ArrayList<>();

    public static void add(Block block) {
        BLOCKS.add(block);
    }

    public static List<Block> getBlocks() {
        return BLOCKS;
    }
}
