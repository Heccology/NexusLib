package net.hecco.heccolib.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.hecco.heccolib.lib.blockFamilyCreator.BlockFamilyCreator;
import net.hecco.heccolib.lib.publicBlocks.PublicStairBlock;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public abstract class HLRecipeProvider extends FabricRecipeProvider {
    private final String MOD_ID;

    public HLRecipeProvider(String modId, FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
        this.MOD_ID = modId;
    }

    public void generateBlockFamilyRecipies(RecipeOutput recipeOutput) {
        for (Supplier<Block> block : BlockFamilyCreator.BLOCKS.values()) {
            if (BuiltInRegistries.BLOCK.getKey(block.get()).getNamespace() == this.MOD_ID) {
                Block block1 = block.get();
                if (block1 instanceof StairBlock) {
                    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, block1, 4)
                            .pattern("#  ")
                            .pattern("## ")
                            .pattern("###")
                            .define('#', BlockFamilyCreator.BLOCK_TO_BLOCK_FAMILY.get(block).VARIANT_TO_BASE_BLOCK.get(block).get())
                            .unlockedBy(getHasName(block1), has(block1))
                            .save(recipeOutput);
                } else if (block1 instanceof SlabBlock) {
                    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, block1, 6)
                            .pattern("###")
                            .define('#', BlockFamilyCreator.BLOCK_TO_BLOCK_FAMILY.get(block).VARIANT_TO_BASE_BLOCK.get(block).get())
                            .unlockedBy(getHasName(block1), has(block1))
                            .save(recipeOutput);
                } else if (block1 instanceof WallBlock) {
                    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, block1, 6)
                            .pattern("###")
                            .pattern("###")
                            .define('#', BlockFamilyCreator.BLOCK_TO_BLOCK_FAMILY.get(block).VARIANT_TO_BASE_BLOCK.get(block).get())
                            .unlockedBy(getHasName(block1), has(block1))
                            .save(recipeOutput);
                }
            }
        }
    }
}
