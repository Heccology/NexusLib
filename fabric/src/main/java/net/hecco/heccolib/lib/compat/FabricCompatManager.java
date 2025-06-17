package net.hecco.heccolib.lib.compat;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.hecco.heccolib.HeccoLib;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class FabricCompatManager extends CompatManager {

    private final Map<String, Consumer<FabricDataGenerator.Pack>> datagenProviders = new HashMap<>();

    public FabricCompatManager(String modId) {
        super(modId);
    }

    public FabricCompatManager() {
        super(HeccoLib.MOD_ID);
    }

    @Override
    public void registerCompatContent() {
        for (CompatModule module : MODULES) {
            module.registerContent();
            ResourceManagerHelper.registerBuiltinResourcePack(
                    ResourceLocation.fromNamespaceAndPath(this.modId, module.modId() + "_dat"),
                    FabricLoader.getInstance().getModContainer(this.modId).get(),
                    Component.translatable("pack." + this.modId + "." + module.modId()),
                    ResourcePackActivationType.ALWAYS_ENABLED
            );
        }
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
