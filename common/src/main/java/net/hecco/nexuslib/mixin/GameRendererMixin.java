package net.hecco.nexuslib.mixin;

import net.hecco.nexuslib.lib.postProcessShaderRegistry.NLPostProcessShaderRegistry;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.function.Function;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow
    private void loadEffect(ResourceLocation resourceLocation) {}

    @Shadow @Nullable
    PostChain postEffect;

    @Inject(method = "checkEntityPostEffect", at = @At("TAIL"))
    private void nexuslib$addEntityPostEffects(Entity entity, CallbackInfo ci) {
        if (this.postEffect == null) {
            Map<Function<Entity, Boolean>, ResourceLocation> nlShaders = NLPostProcessShaderRegistry.getShaders();
            for (Map.Entry<Function<Entity, Boolean>, ResourceLocation> entry : nlShaders.entrySet()) {
                if (entry.getKey().apply(entity)) {
                    this.loadEffect(entry.getValue());
                }
            }
        }
    }
}
