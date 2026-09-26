package net.hecco.nexuslib.mixin;

import net.hecco.nexuslib.lib.selectedItemNametagRegistry.NLSelectedItemNametagRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(Gui.class)
public abstract class GuiMixin {

    @Shadow private ItemStack lastToolHighlight;

    @Shadow @Final private Minecraft minecraft;

    @Shadow public abstract Font getFont();

    @Inject(method = "renderSelectedItemName", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawStringWithBackdrop(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;IIII)I", ordinal = 0), locals = LocalCapture.CAPTURE_FAILEXCEPTION, cancellable = true)
    private void nexuslib$CustomSelectedItemNametags(GuiGraphics guiGraphics, CallbackInfo ci, MutableComponent mutableComponent, int i, int j, int k, int l) {
        if (NLSelectedItemNametagRegistry.getValues().containsKey(lastToolHighlight.getItem())) {
            List<Component> strings = NLSelectedItemNametagRegistry.getValues().get(lastToolHighlight.getItem()).apply(lastToolHighlight, mutableComponent);
            int size = strings.size();
            for (int line = 0; line < size; line++) {
                int offset = line == 0 ? 2 : 0;
                Component component = strings.get(line);
                int xOffset = this.getFont().width(component)/2;
                guiGraphics.drawStringWithBackdrop(getFont(), component, j + (i/2) - xOffset, k - ((size-line-1)*getFont().lineHeight) - offset, i, FastColor.ARGB32.color(l, -1));
            }
            ci.cancel();
            minecraft.getProfiler().pop();
        }
    }
}
