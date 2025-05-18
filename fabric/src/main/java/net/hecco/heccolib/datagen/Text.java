package net.hecco.heccolib.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class Text extends HLBlockTagProvider {
    public Text(String modId, FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(modId, output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        generateBlockFamilyTags();
    }
}
