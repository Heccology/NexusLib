package net.hecco.nexuslib.mixin;

import net.hecco.nexuslib.lib.loader_agnostic.fuelRegistry.NLFuelRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(AbstractFurnaceBlockEntity.class)
public class AbstractFurnaceBlockEntityMixin {
    @Inject(method = "getFuel", at = @At("RETURN"), cancellable = true)
    private static void nexuslib$getFuel(CallbackInfoReturnable<Map<Item, Integer>> cir) {
        Map<Item, Integer> newMap = new HashMap<>();
        if (cir.getReturnValue() != null) {
            newMap.putAll(cir.getReturnValue());
        }
        newMap.putAll(NLFuelRegistry.getFuels());
        cir.setReturnValue(newMap);
    }
}
