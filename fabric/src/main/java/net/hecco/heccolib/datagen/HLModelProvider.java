package net.hecco.heccolib.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;

public abstract class HLModelProvider extends FabricModelProvider {

    private final String MOD_ID;

    public HLModelProvider(String modId, FabricDataOutput output) {
        super(output);
        this.MOD_ID = modId;
    }
}
