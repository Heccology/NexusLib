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
        builder.add("nexuslib.cape.vanilla", "OFF");
        builder.add("nexuslib.cape.bountifulfares", "Bountiful Fares Contributor");
        builder.add("nexuslib.cape.bountifulfares_super", "B.F. Super Contributor");
        builder.add("nexuslib.cape.nexuslib", "NexusLib Contributor");
        builder.add("nexuslib.cape.subterrous", "Subterrous Contributor");
        builder.add("nexuslib.cape.subterrous_super", "Subt. Super Contributor");
        builder.add("nexuslib.cape.feldspar", "Feldspar");
        builder.add("nexuslib.tooltip.cape_button", "Heccology Cape: %s");
    }
}
