package net.hecco.nexuslib.lib.paletteTemplateMatrixSpriteSource;

import com.google.common.base.Suppliers;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.hecco.nexuslib.NexusLib;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.atlas.SpriteResourceLoader;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.SpriteSourceType;
import net.minecraft.client.renderer.texture.atlas.sources.LazyLoadedImage;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceMetadata;
import net.minecraft.util.FastColor;
import org.apache.logging.log4j.util.TriConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.*;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;

public class PaletteTemplateMatrix implements SpriteSource {

    public static final MapCodec<PaletteTemplateMatrix> CODEC = RecordCodecBuilder.mapCodec(
            (p_266838_) -> p_266838_.group(
                    ResourceLocation.CODEC.fieldOf("id").forGetter((p_267300_) -> p_267300_.id),
                    ResourceLocation.CODEC.fieldOf("palette_key").forGetter((p_267300_) -> p_267300_.paletteKey),
                    ResourceLocation.CODEC.fieldOf("palette_definition_directory").forGetter((p_266732_) -> p_266732_.paletteDefinitionDirectory),
                    ResourceLocation.CODEC.fieldOf("template_definition_directory").forGetter((p_266732_) -> p_266732_.templateDefinitionDirectory)
            ).apply(p_266838_, PaletteTemplateMatrix::new));

    private final ResourceLocation id;
    private final ResourceLocation paletteKey;
    private final ResourceLocation paletteDefinitionDirectory;
    private final ResourceLocation templateDefinitionDirectory;

    public PaletteTemplateMatrix(ResourceLocation id, ResourceLocation paletteKey, ResourceLocation paletteDefinitionDirectory, ResourceLocation templateDefinitionDirectory) {
        this.id = id;
        this.paletteKey = paletteKey;
        this.paletteDefinitionDirectory = paletteDefinitionDirectory;
        this.templateDefinitionDirectory = templateDefinitionDirectory;
    }

    @Override
    public void run(ResourceManager resourceManager, Output output) {
        Supplier<int[]> supplier = Suppliers.memoize(() -> loadPaletteEntryFromImage(resourceManager, this.paletteKey));
        Map<ResourceLocation, Supplier<IntUnaryOperator>> map = new HashMap();

        Map<ResourceLocation, ResourceLocation> paletteLocations = discoverPaletteLocations(resourceManager);
        List<ResourceLocation> templateLocations = discoverTemplateLocations(resourceManager);

        for (Map.Entry<ResourceLocation,ResourceLocation> palette : paletteLocations.entrySet()) {
            map.put(palette.getKey(), Suppliers.memoize(() -> createPaletteMapping(supplier.get(), loadPaletteEntryFromImage(resourceManager, palette.getValue()))));
        }

        for (ResourceLocation template : templateLocations) {
            ResourceLocation resourcelocation = TEXTURE_ID_CONVERTER.idToFile(template);
            Optional<Resource> optional = resourceManager.getResource(resourcelocation);
            if (optional.isEmpty()) {
                NexusLib.LOGGER.warn("Unable to find texture {}", resourcelocation);
            } else {
                LazyLoadedImage lazyloadedimage = new LazyLoadedImage(resourcelocation, optional.get(), map.size());
                for(Map.Entry<ResourceLocation, Supplier<IntUnaryOperator>> entry : map.entrySet()) {
                    ResourceLocation resourcelocation2 = resourcelocation.withPath(resourcelocation.getPath().replace(".png", "").replace("textures/", "")).withSuffix("_" + entry.getKey().getNamespace() + "_" + entry.getKey().getPath());
                    output.add(resourcelocation2, new PaletteTemplateSpriteSupplier(lazyloadedimage, entry.getValue(), resourcelocation2));
                }
            }
        }
    }

    private static IntUnaryOperator createPaletteMapping(int[] template, int[] palette) {
        if (palette.length != template.length) {
            NexusLib.LOGGER.warn("Palette mapping has different sizes: {} and {}", template.length, palette.length);
            throw new IllegalArgumentException();
        } else {
            Int2IntMap int2intmap = new Int2IntOpenHashMap(palette.length);

            for(int i = 0; i < template.length; ++i) {
                int j = template[i];
                if (FastColor.ABGR32.alpha(j) != 0) {
                    int2intmap.put(FastColor.ABGR32.transparent(j), palette[i]);
                }
            }

            return (p_267899_) -> {
                int k = FastColor.ABGR32.alpha(p_267899_);
                if (k == 0) {
                    return p_267899_;
                } else {
                    int l = FastColor.ABGR32.transparent(p_267899_);
                    int i1 = int2intmap.getOrDefault(l, FastColor.ABGR32.opaque(l));
                    int j1 = FastColor.ABGR32.alpha(i1);
                    return FastColor.ABGR32.color(k * j1 / 255, i1);
                }
            };
        }
    }

    private Map<ResourceLocation, ResourceLocation> discoverPaletteLocations(ResourceManager resourceManager) {
        Map<ResourceLocation, Resource> discovered = resourceManager.listResources(
                paletteDefinitionDirectory.getPath(),
                id -> id.getPath().endsWith(".json")
                );

        Map<ResourceLocation, ResourceLocation> paletteLocations = new HashMap<>();

        for (Map.Entry<ResourceLocation, Resource> entry : discovered.entrySet()) {
            try (Reader reader = entry.getValue().openAsReader()) {
                JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                if (!json.has("palette_location")) {
                    NexusLib.LOGGER.warn("Invalid palette definition resource {}: missing 'palette_location' field", entry.getKey());
                    continue;
                }
                String rawPaletteLocation = json.get("palette_location").getAsString();
                ResourceLocation paletteLocation = ResourceLocation.parse(rawPaletteLocation);

                if (!json.has("definition")) {
                    NexusLib.LOGGER.warn("Invalid palette definition resource {}: missing 'definition' field", entry.getKey());
                    continue;
                }
                String rawDefinition = json.get("definition").getAsString();
                ResourceLocation definition = ResourceLocation.parse(rawDefinition);

                paletteLocations.put(definition, paletteLocation);

                for (TriConsumer<ResourceLocation, ResourceLocation, JsonObject> consumer : PTMEvents.ON_PALETTE_DISCOVERY) {
                    try {
                        consumer.accept(this.id, paletteLocation, json);
                    } catch (Exception ignored) {}
                }
            } catch (IOException e) {
                NexusLib.LOGGER.error("Invalid palette definition resource {}", entry.getKey(), e);
            }
        }
        return paletteLocations;
    }

    private List<ResourceLocation> discoverTemplateLocations(ResourceManager resourceManager) {
        Map<ResourceLocation, Resource> discovered = resourceManager.listResources(
                templateDefinitionDirectory.getPath(),
                id -> id.getPath().endsWith(".json")
        );

        List<ResourceLocation> templateLocations = new ArrayList<>();

        for (Map.Entry<ResourceLocation, Resource> entry : discovered.entrySet()) {
            try (Reader reader = entry.getValue().openAsReader()) {
                JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                if (!json.has("template_location")) {
                    NexusLib.LOGGER.warn("Invalid template definition resource {}: missing 'template_location' field", entry.getKey());
                    continue;
                }
                String raw = json.get("template_location").getAsString();
                ResourceLocation templateLocation = ResourceLocation.parse(raw);
                templateLocations.add(templateLocation);

                for (TriConsumer<ResourceLocation, ResourceLocation, JsonObject> consumer : PTMEvents.ON_TEMPLATE_DISCOVERY) {
                    try {
                        consumer.accept(this.id, templateLocation, json);
                    } catch (Exception ignored) {}
                }

            } catch (IOException e) {
                NexusLib.LOGGER.error("Invalid template definition resource {}", entry.getKey(), e);
            }
        }
        return templateLocations;
    }

    public static int[] loadPaletteEntryFromImage(ResourceManager resourceMananger, ResourceLocation palette) {
        Optional<Resource> optional = resourceMananger.getResource(TEXTURE_ID_CONVERTER.idToFile(palette));
        if (optional.isEmpty()) {
            NexusLib.LOGGER.error("Failed to load palette image {}", palette);
            throw new IllegalArgumentException();
        } else {
            try {
                int[] aint;
                try (
                        InputStream inputstream = optional.get().open();
                        NativeImage nativeimage = NativeImage.read(inputstream)
                ) {
                    aint = nativeimage.getPixelsRGBA();
                }

                return aint;
            } catch (Exception exception) {
                NexusLib.LOGGER.error("Couldn't load texture {}", palette, exception);
                throw new IllegalArgumentException();
            }
        }
    }

    @Override
    public @NotNull SpriteSourceType type() {
        return NLSpriteSources.PALETTE_TEMPLATE_MATRIX;
    }

    record PaletteTemplateSpriteSupplier(LazyLoadedImage baseImage, Supplier<IntUnaryOperator> palette, ResourceLocation permutationLocation) implements SpriteSource.SpriteSupplier {

        @Nullable
        public SpriteContents apply(SpriteResourceLoader p_295023_) {
            Object object;
            try {
                NativeImage nativeimage = this.baseImage.get().mappedCopy(this.palette.get());
                SpriteContents var4 = new SpriteContents(this.permutationLocation, new FrameSize(nativeimage.getWidth(), nativeimage.getHeight()), nativeimage, ResourceMetadata.EMPTY);
                return var4;
            } catch (IOException | IllegalArgumentException ioexception) {
                NexusLib.LOGGER.error("unable to apply palette to {}", this.permutationLocation, ioexception);
                object = null;
            } finally {
                this.baseImage.release();
            }

            return (SpriteContents)object;
        }

        public void discard() {
            this.baseImage.release();
        }
    }
}
