package net.hecco.heccolib.mixin;

import net.hecco.heccolib.HeccoLib;
import net.hecco.heccolib.lib.compat.HLCompatAPI;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureElement;
import net.minecraft.world.flag.FeatureFlagSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FeatureElement.class)
public interface FeatureElementMixin {
    @Inject(
            method = "isEnabled(Lnet/minecraft/world/flag/FeatureFlagSet;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private void heccolib$addCompatLogic(FeatureFlagSet enabledFeatures, CallbackInfoReturnable<Boolean> cir) {
        FeatureElement self = (FeatureElement)(Object)this;
        ResourceLocation id = HLCompatAPI.getId(self);
        if (id != null && !id.getNamespace().equals("minecraft")) {
            boolean i = HLCompatAPI.check(self);
            if (i) {
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
    }
}
