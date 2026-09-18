package net.hecco.nexuslib.lib.paletteTemplateMatrixSpriteSource;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Function4;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.ArrayList;
import java.util.List;

public class PTMEvents {
    protected static final List<Function4<ResourceLocation, ResourceLocation, ResourceLocation, JsonObject, Boolean>> PALETTE_DISCOVERY_CONDITIONS = new ArrayList<>();
    protected static final List<Function4<ResourceLocation, ResourceLocation, ResourceLocation, JsonObject, Boolean>> TEMPLATE_DISCOVERY_CONDITIONS = new ArrayList<>();

    protected static final List<TriConsumer<ResourceLocation, ResourceLocation, JsonObject>> ON_PALETTE_DISCOVERY = new ArrayList<>();
    protected static final List<TriConsumer<ResourceLocation, ResourceLocation, JsonObject>> ON_TEMPLATE_DISCOVERY = new ArrayList<>();

    public static void registerPaletteDiscoveryCondition(Function4<ResourceLocation, ResourceLocation, ResourceLocation, JsonObject, Boolean> condition) {
        PALETTE_DISCOVERY_CONDITIONS.add(condition);
    }
    public static void registerTemplateDiscoveryCondition(Function4<ResourceLocation, ResourceLocation, ResourceLocation, JsonObject, Boolean> condition) {
        TEMPLATE_DISCOVERY_CONDITIONS.add(condition);
    }
    public static void registerOnPaletteDiscoveryEvent(TriConsumer<ResourceLocation, ResourceLocation, JsonObject> consumer) {
        ON_PALETTE_DISCOVERY.add(consumer);
    }
    public static void registerOnTemplateDiscoveryEvent(TriConsumer<ResourceLocation, ResourceLocation, JsonObject> consumer) {
        ON_TEMPLATE_DISCOVERY.add(consumer);
    }
}
