package net.hecco.nexuslib.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import net.hecco.nexuslib.lib.postProcessShaderRegistry.NLPostProcessShaderRegistry;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {

    @Redirect(method = "keyPress", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;togglePostEffect()V"))
    private void nexuslib$fixToggleShadersGamemodeSwitcherIssue(GameRenderer instance) {
        if (
                !InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 292) && //fixed your game mojang....again
                instance.currentEffect() != null &&
                NLPostProcessShaderRegistry.SHADER_TO_TOGGLEABLE.getOrDefault(instance.currentEffect().getName(), (player) -> true).apply(Minecraft.getInstance().player)
        ) {
            instance.togglePostEffect();
        }
    }
}
