package net.hecco.nexuslib.mixin;

import net.hecco.nexuslib.lib.itemModels.NLModelLoadingRegistry;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelBakery.class)
public abstract class ModelLoaderMixin {

    @Shadow
    protected abstract void loadSpecialItemModelAndDependencies(ModelResourceLocation modelLocation);

    @Inject(method = "<init>", at = {@At(value = "INVOKE", ordinal = 0, target = "net/minecraft/client/resources/model/ModelBakery.loadSpecialItemModelAndDependencies (Lnet/minecraft/client/resources/model/ModelResourceLocation;)V")})
    private void nexuslib$addModels(CallbackInfo info) {
        for (ModelResourceLocation resourceLocation : NLModelLoadingRegistry.getEntries()) {
            loadSpecialItemModelAndDependencies(resourceLocation);
        }
    }
}