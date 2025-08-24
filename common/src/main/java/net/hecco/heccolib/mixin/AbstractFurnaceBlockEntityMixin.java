package net.hecco.heccolib.mixin;

import net.hecco.heccolib.HeccoLib;
import net.hecco.heccolib.lib.fuelRegistry.HLFuelRegistry;
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
    private static void heccolib$getFuel(CallbackInfoReturnable<Map<Item, Integer>> cir) {
        Map<Item, Integer> newMap = new HashMap<>();
        HeccoLib.LOGGER.info("heccolib is cool");
        HeccoLib.LOGGER.info(cir.getReturnValue().toString());
        HeccoLib.LOGGER.info(HLFuelRegistry.getFuels().toString());
        if (cir.getReturnValue() != null) {
            newMap.putAll(cir.getReturnValue());
        }
        newMap.putAll(HLFuelRegistry.getFuels());
        cir.setReturnValue(newMap);
    }
}
