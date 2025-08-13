package net.hecco.heccolib.mixin;

import net.hecco.heccolib.lib.fuelRegistry.HLFuelRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.LinkedHashMap;
import java.util.Map;

@Mixin(AbstractFurnaceBlockEntity.class)
public class AbstractFurnaceBlockEntityMixin {
    @Inject(method = "getFuel", at = @At("RETURN"), cancellable = true)
    private static void heccolib$addFuels(CallbackInfoReturnable<Map<Item, Integer>> cir) {
        Map<Item, Integer> map = new LinkedHashMap<>(cir.getReturnValue());
        map.putAll(HLFuelRegistry.getFuels());
        cir.setReturnValue(map);
    }
}
