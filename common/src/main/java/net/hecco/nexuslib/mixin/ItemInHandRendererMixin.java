package net.hecco.nexuslib.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.hecco.nexuslib.lib.util.NLItemUtility.HAND_ANIMATION_IGNORED_COMPONENTS;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow private ItemStack mainHandItem;

    @Shadow private ItemStack offHandItem;

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getOffhandItem()Lnet/minecraft/world/item/ItemStack;", shift = At.Shift.AFTER))
    private void subterrous$ignoreTransponderData(CallbackInfo ci) {
        LocalPlayer localplayer = minecraft.player;
        ItemStack itemstack = localplayer.getMainHandItem();
        ItemStack itemstack1 = localplayer.getOffhandItem();
        if (subterrous$transponderCompassesMatch(mainHandItem, itemstack)) {
            this.mainHandItem = itemstack;
        }
        if (subterrous$transponderCompassesMatch(offHandItem, itemstack1)) {
            this.offHandItem = itemstack1;
        }
    }

    @Unique
    private static boolean subterrous$transponderCompassesMatch(ItemStack oldStack, ItemStack newStack) {
        ItemStack oldCopy = oldStack.copy();
        ItemStack newCopy = newStack.copy();

        for (TypedDataComponent<?> component : oldCopy.getComponents()) {
            if (newCopy.has(component.type()) && HAND_ANIMATION_IGNORED_COMPONENTS.contains(component.type())) {
                oldCopy.remove(component.type());
                newCopy.remove(component.type());
            }
        }

        return ItemStack.matches(oldCopy, newCopy);
    }
}
