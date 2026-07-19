package net.hecco.nexuslib.lib.dripstoneConversionRegistry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import oshi.util.tuples.Pair;

import java.util.HashMap;
import java.util.Map;

public class NLDripstoneConversionRegistry {
    private static final Map<Block, Pair<Fluid, BlockState>> CONVERSIONS = new HashMap<>();

    public static void add(Block inputState, Fluid fluidType, BlockState resultState) {
        CONVERSIONS.put(inputState, new Pair<>(fluidType, resultState));
    }

    public static Map<Block, Pair<Fluid, BlockState>> getValues() {
        return CONVERSIONS;
    }
}
