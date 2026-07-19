package net.hecco.nexuslib.mixin;

import net.hecco.nexuslib.lib.loader_agnostic.fluidInteractionRegistry.NLFluidInteractionRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import org.apache.commons.lang3.function.TriFunction;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LiquidBlock.class)
public abstract class LiquidBlockMixin {
    @Shadow @Final
    public FlowingFluid fluid;

    @Inject(method = "onPlace", at = @At("HEAD"), cancellable = true)
    private void nexuslib$fluidInteractionOnPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving, CallbackInfo ci) {
        for (TriFunction<FlowingFluid, Level, BlockPos, Boolean> function : NLFluidInteractionRegistry.getValues()) {
            if (function.apply(this.fluid, level, pos)) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "neighborChanged", at = @At("HEAD"), cancellable = true)
    private void nexuslib$fluidInteractionOnPlace(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving, CallbackInfo ci) {
        for (TriFunction<FlowingFluid, Level, BlockPos, Boolean> function : NLFluidInteractionRegistry.getValues()) {
            if (function.apply(this.fluid, level, pos)) {
                ci.cancel();
            }
        }
    }
}
