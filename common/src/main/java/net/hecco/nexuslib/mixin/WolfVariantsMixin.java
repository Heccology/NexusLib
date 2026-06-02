package net.hecco.nexuslib.mixin;

import net.hecco.nexuslib.NexusLib;
import net.hecco.nexuslib.registry.NLTags;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.animal.WolfVariant;
import net.minecraft.world.entity.animal.WolfVariants;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Mixin(WolfVariants.class)
public class WolfVariantsMixin {

    @Shadow @Final public static ResourceKey<WolfVariant> DEFAULT;

    @Inject(method = "getSpawnVariant", at = @At("HEAD"), cancellable = true)
    private static void nexuslib$wolfVariantOverrides(RegistryAccess registryAccess, Holder<Biome> biome, CallbackInfoReturnable<Holder<WolfVariant>> cir) {
        if (biome.is(NLTags.BiomeTags.OVERRIDES_WOLF_VARIANT)) {
            Registry<WolfVariant> registry = registryAccess.registryOrThrow(Registries.WOLF_VARIANT);
            for (Map.Entry<String, TagKey<Biome>> tag : NLTags.BiomeTags.HAS_WOLF_VARIANTS.entrySet()) {
                Optional<Holder.Reference<WolfVariant>> possibleVariant = registry.holders().filter((variant) -> {
                    return (Arrays.stream(variant.getRegisteredName().split(":")).toList().getLast()).equals(tag.getKey()) && biome.is(tag.getValue());
                }).findFirst().or(() -> registry.getHolder(DEFAULT));
                if (possibleVariant.isPresent()) {
                    cir.setReturnValue(possibleVariant.or(registry::getAny).orElseThrow());
                    cir.cancel();
                    break;
                }
            }
        }
    }
}
