package net.hecco.nexuslib.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.hecco.nexuslib.lib.util.NLUtility;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public abstract class NLLanguageProvider extends FabricLanguageProvider {

    private final String MOD_ID;

    protected NLLanguageProvider(String modId, FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
        this.MOD_ID = modId;
    }

    Set<String> usedTranslationKeys = new HashSet<>();

    public void generate(TranslationBuilder translationBuilder, String key, String translation) {
        if(usedTranslationKeys.contains(key)) {
            return;
        }
        translationBuilder.add(key, translation);
        usedTranslationKeys.add(key);
    }

    public void generate(TranslationBuilder translationBuilder, Block block, String translation) {
        generate(translationBuilder, block.getDescriptionId(), translation);
    }

    public void generate(TranslationBuilder translationBuilder, Item item, String translation) {
        generate(translationBuilder, item.getDescriptionId(), translation);
    }


    public void generateBlocksandItems(TranslationBuilder translationBuilder) {
        for (ResourceLocation id : NLUtility.allBlockIdsInNamespace(this.MOD_ID)) {
            String key = BuiltInRegistries.BLOCK.get(id).getDescriptionId();
            if (usedTranslationKeys.contains(key)) {
                continue;
            }

            usedTranslationKeys.add(key);
            translationBuilder.add(key, NLUtility.toSentanceCase(id.getPath()));
        }

        for (ResourceLocation id : NLUtility.allItemIdsInNamespace(this.MOD_ID)) {
            String key = BuiltInRegistries.ITEM.get(id).getDescriptionId();
            if (usedTranslationKeys.contains(key)) {
                continue;
            }

            usedTranslationKeys.add(key);
            translationBuilder.add(key, NLUtility.toSentanceCase(id.getPath()));
        }
    }
}
