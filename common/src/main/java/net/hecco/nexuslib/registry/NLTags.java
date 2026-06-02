package net.hecco.nexuslib.registry;

import net.hecco.nexuslib.NexusLib;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import java.util.Map;

public class NLTags {
    public static class BiomeTags {
        public static final TagKey<Biome> OVERRIDES_WOLF_VARIANT = createBiomeTag("overrides_wolf_variant");
        public static final Map<String, TagKey<Biome>> HAS_WOLF_VARIANTS = Map.of(
                "ashen", createBiomeTag("has_wolf_ashen_variant"),
                "black", createBiomeTag("has_wolf_black_variant"),
                "chestnut", createBiomeTag("has_wolf_chestnut_variant"),
                "rusty", createBiomeTag("has_wolf_rusty_variant"),
                "snowy", createBiomeTag("has_wolf_snowy_variant"),
                "spotted", createBiomeTag("has_wolf_spotted_variant"),
                "striped", createBiomeTag("has_wolf_striped_variant"),
                "woods", createBiomeTag("has_wolf_woods_variant")
        );

        private static TagKey<Biome> createBiomeTag(String name) {
            return TagKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(NexusLib.MOD_ID, name));
        }
    }
}
