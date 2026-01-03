package net.hecco.nexuslib.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Function3;
import net.hecco.nexuslib.lib.itemModels.NLItemModelOverrideRegistry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.ArrayList;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @ModifyVariable(method = "render", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel nexuslib$render(BakedModel value, ItemStack stack, ItemDisplayContext renderMode, boolean leftHanded, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        ArrayList<Function3<ItemStack, ItemDisplayContext, Boolean, BakedModel>> overrides = NLItemModelOverrideRegistry.getEntries();
        for (Function3<ItemStack, ItemDisplayContext, Boolean, BakedModel> function : overrides) {
            BakedModel model = function.apply(stack, renderMode, leftHanded);
            if (model != null) {
                return model;
            }
        }
        return value;
    }
}
