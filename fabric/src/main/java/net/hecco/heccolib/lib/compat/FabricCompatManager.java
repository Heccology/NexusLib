package net.hecco.heccolib.lib.compat;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class FabricCompatManager extends CompatManager {

    private final Map<String, Consumer<FabricDataGenerator.Pack>> datagenProviders = new HashMap<>();

    public FabricCompatManager(String modId) {
        super(modId);
    }

    public void registerDatagenProviders(String moduleId, Consumer<FabricDataGenerator.Pack> pack) {
        datagenProviders.put(moduleId, pack);
    }

    public void generateDatapacks(FabricDataGenerator fabricDataGenerator) {
        for (CompatModule module : MODULES) {
            if (datagenProviders.get(module.modId()) != null) {
                FabricDataGenerator.Pack pack = fabricDataGenerator.createBuiltinResourcePack(ResourceLocation.fromNamespaceAndPath(this.modId, module.modId() + "_dat"));
                datagenProviders.get(module.modId()).accept(pack);
            }
        }
    }
}
