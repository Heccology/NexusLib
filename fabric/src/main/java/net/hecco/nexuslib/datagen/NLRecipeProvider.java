package net.hecco.nexuslib.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.hecco.nexuslib.lib.blockFamilyCreator.BlockFamilyCreator;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.level.block.*;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public abstract class NLRecipeProvider extends FabricRecipeProvider {
    private final String MOD_ID;

    public NLRecipeProvider(String modId, FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
        this.MOD_ID = modId;
    }

    protected Set<Supplier<Block>> usedBlockRecipes = new HashSet<>();

    public void generateBlockFamilyRecipes(RecipeOutput recipeOutput) {
        for (Supplier<Block> block : BlockFamilyCreator.BLOCKS.values()) {
            if (usedBlockRecipes.stream().noneMatch(supplier -> block.get() == supplier.get())) {
                if (BuiltInRegistries.BLOCK.getKey(block.get()).getNamespace().equals(this.MOD_ID)) {
                    Block block1 = block.get();
                    if (block1 instanceof StairBlock) {
                        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, block1, 4)
                                .pattern("#  ")
                                .pattern("## ")
                                .pattern("###")
                                .define('#', BlockFamilyCreator.BLOCK_TO_BLOCK_FAMILY.get(block).VARIANT_TO_BASE_BLOCK.get(block).get())
                                .unlockedBy(getHasName(block1), has(block1))
                                .group(BlockFamilyCreator.BLOCK_TO_BLOCK_FAMILY.get(block).BLOCK_TO_RECIPE_GROUP.get(block))
                                .save(recipeOutput);
                    } else if (block1 instanceof SlabBlock) {
                        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, block1, 6)
                                .pattern("###")
                                .define('#', BlockFamilyCreator.BLOCK_TO_BLOCK_FAMILY.get(block).VARIANT_TO_BASE_BLOCK.get(block).get())
                                .unlockedBy(getHasName(block1), has(block1))
                                .group(BlockFamilyCreator.BLOCK_TO_BLOCK_FAMILY.get(block).BLOCK_TO_RECIPE_GROUP.get(block))
                                .save(recipeOutput);
                    } else if (block1 instanceof WallBlock) {
                        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, block1, 6)
                                .pattern("###")
                                .pattern("###")
                                .define('#', BlockFamilyCreator.BLOCK_TO_BLOCK_FAMILY.get(block).VARIANT_TO_BASE_BLOCK.get(block).get())
                                .unlockedBy(getHasName(block1), has(block1))
                                .group(BlockFamilyCreator.BLOCK_TO_BLOCK_FAMILY.get(block).BLOCK_TO_RECIPE_GROUP.get(block))
                                .save(recipeOutput);
                    }
                }
            }
        }
    }
}
