package net.hecco.nexuslib.mixin;

import net.hecco.nexuslib.lib.compat.NLCompatAPI;
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
    private void nexuslib$addCompatLogic(FeatureFlagSet enabledFeatures, CallbackInfoReturnable<Boolean> cir) {
        FeatureElement self = (FeatureElement)(Object)this;
        ResourceLocation id = NLCompatAPI.getId(self);
        if (id != null && !id.getNamespace().equals("minecraft")) {
            cir.setReturnValue(!NLCompatAPI.DISABLED_CACHE.contains(id));
        }
    }
}
