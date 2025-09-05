package net.hecco.heccolib.lib.compat;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.hecco.heccolib.HeccoLib;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;

public class FabricHLCompatAPI {
    public static void generateCompatDatapacks(FabricDataGenerator dataGenerator, CompatManager manager) {
        for (ModIntegration integration : manager.getIntegrations()) {
            if (integration.shouldCreateDatapack()) {
                if (integration.modIds().isEmpty()) {
                    HeccoLib.LOGGER.error("Cannot create datapack with no mod ids for integration " + integration);
                    continue;
                }
                StringBuilder id = new StringBuilder();
                for (String modId : integration.modIds()) {
                    id.append(modId).append("_");
                }
                id.append("dat");
                var pack = dataGenerator.createBuiltinResourcePack(ResourceLocation.fromNamespaceAndPath(manager.modId, id.toString()));
                pack.addProvider((a, b) -> new FabricRecipeProvider(a, b) {
                    @Override
                    public void buildRecipes(RecipeOutput exporter) {
                        integration.recipeGeneration(exporter);
                    }
                });
            }
        }
    }
}
