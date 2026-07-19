package net.hecco.nexuslib.lib.loader_agnostic.fluidInteractionRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FlowingFluid;
import org.apache.commons.lang3.function.TriFunction;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.world.level.block.LiquidBlock.POSSIBLE_FLOW_DIRECTIONS;

public class NLFluidInteractionRegistry {
    private static final List<TriFunction<FlowingFluid, Level, BlockPos, Boolean>> INTERACTIONS = new ArrayList<>();

    public static void add(TriFunction<FlowingFluid, Level, BlockPos, Boolean> function) {
        INTERACTIONS.add(function);
    }

    public static List<TriFunction<FlowingFluid, Level, BlockPos, Boolean>> getValues() {
        return INTERACTIONS;
    }

    public static boolean isLavaAndContactsWater(FlowingFluid fluid, Level level, BlockPos pos) {
        if (fluid.is(FluidTags.LAVA)) {
            for (Direction direction : POSSIBLE_FLOW_DIRECTIONS) {
                BlockPos blockPos = pos.relative(direction);
                if (level.getFluidState(blockPos).is(FluidTags.WATER)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean fizzAndFinish(Level level, BlockPos pos) {
        level.levelEvent(1501, pos, 0);
        return true;
    }
}
