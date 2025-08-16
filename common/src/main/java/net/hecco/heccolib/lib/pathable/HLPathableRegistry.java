package net.hecco.heccolib.lib.pathable;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class HLPathableRegistry {
    private static final Map<Block, BlockState> PATHS = new LinkedHashMap<>();

    public static void add(Block block, Block path) {
        PATHS.put(block, path.defaultBlockState());
    }

    public static void add(Supplier<Block> block, Supplier<Block> path) {
        add(block.get(), path.get());
    }

    public static Map<Block, BlockState> getPathables() { return PATHS; }
}
