package net.hecco.nexuslib.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.hecco.nexuslib.lib.toolAction.NLToolActions;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Map;
import java.util.Optional;

@Mixin(AxeItem.class)
public class AxeItemMixin {
    // thanks neoforge for having events for literally everything but good content - artyrian
    @ModifyVariable(method = "evaluateNewBlockState", at = @At("STORE"), ordinal = 0)
    private Optional<BlockState> tryWrapWithHLBlocks(Optional<BlockState> value, @Local(argsOnly = true) BlockState state) {
        if (value.isEmpty()) {
            Map<Block, Block> STRIPS_2 = NLToolActions.getStrippables();

            Optional<BlockState> stripgrab = Optional.ofNullable(STRIPS_2.get(state.getBlock())).map((block) -> {
                return block.defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS));
            });

            if (stripgrab.isPresent()) return stripgrab;
        }

        return value;
    }
}