package net.hecco.nexuslib.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class NLLangGen extends FabricLanguageProvider {
    public NLLangGen(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder builder) {
        builder.add("nexuslib.cape.vanilla", "Vanilla Cape");
        builder.add("nexuslib.cape.bountifulfares", "Bountiful Fares");
        builder.add("nexuslib.cape.bountifulfares_super", "Bountiful Fares Super");
        builder.add("nexuslib.cape.nexuslib", "NexusLib");
        builder.add("nexuslib.cape.subterrous", "Subterrous");
        builder.add("nexuslib.cape.subterrous_super", "Subterrous Super");
        builder.add("nexuslib.tooltip.cape_button", "NexusLib Cape: %s");
    }
}
