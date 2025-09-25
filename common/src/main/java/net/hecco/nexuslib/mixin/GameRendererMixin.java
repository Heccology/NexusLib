package net.hecco.nexuslib.mixin;

import net.hecco.nexuslib.lib.postProcessShaderRegistry.NLPostProcessShaderRegistry;
import net.hecco.nexuslib.platform.NLServices;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
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

    @Shadow public abstract void checkEntityPostEffect(@Nullable Entity entity);

    @Shadow @Final private Minecraft minecraft;

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

    @Inject(method = "shutdownEffect", at = @At("TAIL"))
    private void nexuslib$tryEffectsAgain(CallbackInfo ci) {
        if (NLServices.PLATFORM.isModLoaded("supplementaries")) {
            checkEntityPostEffect(this.minecraft.getCameraEntity());
        }
    }
}
