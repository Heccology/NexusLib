package net.hecco.nexuslib.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.hecco.nexuslib.lib.dripstoneConversionRegistry.NLDripstoneConversionRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import oshi.util.tuples.Pair;

import java.util.Optional;

@Mixin(PointedDripstoneBlock.class)
public abstract class PointedDripstoneBlockMixin {

    @Shadow
    private static boolean isStalactite(BlockState state) {
        return false;
    }

    @Shadow
    private static Optional<BlockPos> findRootBlock(Level level, BlockPos pos, BlockState state, int maxIterations) {
        return Optional.empty();
    }

    @Inject(method = "maybeTransferFluid", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/PointedDripstoneBlock;findTip(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;IZ)Lnet/minecraft/core/BlockPos;", shift = At.Shift.AFTER), cancellable = true)
    private static void nexuslib$conversions(BlockState state, ServerLevel level, BlockPos pos, float randChance, CallbackInfo ci, @Local BlockPos blockPos, @Local Optional<PointedDripstoneBlock.FluidInfo> optional) {
        if (optional.isPresent() && blockPos != null) {
            PointedDripstoneBlock.FluidInfo info = optional.get();
            Pair<Fluid, BlockState> conversion = NLDripstoneConversionRegistry.getValues().get(info.sourceState().getBlock());
            if (conversion != null) {
                level.setBlockAndUpdate(info.pos(), conversion.getB());
                level.gameEvent(GameEvent.BLOCK_CHANGE, info.pos(), GameEvent.Context.of(conversion.getB()));
                level.levelEvent(1504, blockPos, 0);
                ci.cancel();
            }
        }
    }

    @Inject(method = "getFluidAboveStalactite", at = @At("HEAD"), cancellable = true)
    private static void nexuslib$conversionFluids(Level level, BlockPos pos, BlockState state, CallbackInfoReturnable<Optional<PointedDripstoneBlock.FluidInfo>> cir) {
        if (isStalactite(state)) {
            findRootBlock(level, pos, state, 11).map((p_221876_) -> {
                BlockPos blockpos = p_221876_.above();
                BlockState blockstate = level.getBlockState(blockpos);
                Fluid fluid = level.getFluidState(blockpos).getType();

                Pair<Fluid, BlockState> conversion = NLDripstoneConversionRegistry.getValues().get(blockstate.getBlock());
                if (conversion != null && !(conversion.getA().is(FluidTags.WATER) && level.dimensionType().ultraWarm())) {
                    fluid = conversion.getA();
                }

                cir.setReturnValue(Optional.of(new PointedDripstoneBlock.FluidInfo(blockpos, fluid, blockstate)));
                cir.cancel();
                return null;
            });
        }
    }
}
