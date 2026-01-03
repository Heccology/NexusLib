package net.hecco.nexuslib.mixin;

import net.hecco.nexuslib.lib.loader_agnostic.defaultAttributeRegistry.NLDefaultAttributeRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Mixin(DefaultAttributes.class)
public class DefaultAttributesMixin {

    @Inject(method = "getSupplier", at = @At("HEAD"), cancellable = true)
    private static void nexuslib$getDefaultAttributes(EntityType<? extends LivingEntity> livingEntity, CallbackInfoReturnable<AttributeSupplier> cir) {
        Map<Supplier<?>, AttributeSupplier> newSuppliers = NLDefaultAttributeRegistry.getValues();
        var unwrapped = newSuppliers.entrySet().stream().map((entry) -> Map.entry(entry.getKey().get(), entry.getValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (unwrapped.containsKey(livingEntity)) {
            cir.setReturnValue(unwrapped.get(livingEntity));
            cir.cancel();
        }
    }

    @Inject(method = "hasSupplier", at = @At("HEAD"), cancellable = true)
    private static void nexuslib$hasDefaultAttributes(EntityType<?> entityType, CallbackInfoReturnable<Boolean> cir) {
        Map<Supplier<?>, AttributeSupplier> newSuppliers = NLDefaultAttributeRegistry.getValues();
        var unwrapped = newSuppliers.entrySet().stream().map((entry) -> Map.entry(entry.getKey().get(), entry.getValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (unwrapped.containsKey(entityType)) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
