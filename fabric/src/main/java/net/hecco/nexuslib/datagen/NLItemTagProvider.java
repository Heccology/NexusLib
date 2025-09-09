package net.hecco.nexuslib.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.hecco.nexuslib.lib.blockFamilyCreator.BlockFamilyCreator;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public abstract class NLItemTagProvider extends FabricTagProvider.ItemTagProvider {

    private final String MOD_ID;

    public NLItemTagProvider(String modId, FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
        this.MOD_ID = modId;
    }

    public void generateBlockFamilyItemTags() {
        for (BlockFamilyCreator blockFamily : BlockFamilyCreator.BLOCK_FAMILIES.values()) {
            if (blockFamily.isOfMod(this.MOD_ID)) {
                for (Supplier<Block> block : blockFamily.WALLS) {
                    getOrCreateTagBuilder(ItemTags.WALLS).add(block.get().asItem());
                }
                for (Supplier<Block> block : blockFamily.STAIRS) {
                    getOrCreateTagBuilder(ItemTags.STAIRS).add(block.get().asItem());
                }
                for (Supplier<Block> block : blockFamily.SLABS) {
                    getOrCreateTagBuilder(ItemTags.SLABS).add(block.get().asItem());
                }
                for (Supplier<Block> block : blockFamily.FENCES) {
                    getOrCreateTagBuilder(ItemTags.FENCES).add(block.get().asItem());
                }
                for (Supplier<Block> block : blockFamily.WOODEN_FENCES) {
                    getOrCreateTagBuilder(ItemTags.WOODEN_FENCES).add(block.get().asItem());
                }
                for (Supplier<Block> block : blockFamily.FENCE_GATES) {
                    getOrCreateTagBuilder(ItemTags.FENCE_GATES).add(block.get().asItem());
                }
                for (Supplier<Block> block : blockFamily.WOODEN_DOORS) {
                    getOrCreateTagBuilder(ItemTags.WOODEN_DOORS).add(block.get().asItem());
                }
                for (Supplier<Block> block : blockFamily.DOORS) {
                    getOrCreateTagBuilder(ItemTags.DOORS).add(block.get().asItem());
                }
                for (Supplier<Block> block : blockFamily.WOODEN_TRAPDOORS) {
                    getOrCreateTagBuilder(ItemTags.WOODEN_TRAPDOORS).add(block.get().asItem());
                }
                for (Supplier<Block> block : blockFamily.TRAPDOORS) {
                    getOrCreateTagBuilder(ItemTags.TRAPDOORS).add(block.get().asItem());
                }
                for (Supplier<Block> block : blockFamily.WOODEN_PRESSURE_PLATES) {
                    getOrCreateTagBuilder(ItemTags.WOODEN_PRESSURE_PLATES).add(block.get().asItem());
                }
                for (Supplier<Block> block : blockFamily.WOODEN_BUTTONS) {
                    getOrCreateTagBuilder(ItemTags.WOODEN_BUTTONS).add(block.get().asItem());
                }
                for (Supplier<Block> block : blockFamily.STONE_BUTTONS) {
                    getOrCreateTagBuilder(ItemTags.STONE_BUTTONS).add(block.get().asItem());
                }
                for (Supplier<Block> block : blockFamily.BUTTONS) {
                    getOrCreateTagBuilder(ItemTags.BUTTONS).add(block.get().asItem());
                }
                for (TagKey<Item> tag : blockFamily.FLAMMABLE_LOG_ITEM_TAGS) {
                    String name = tag.location().getPath().replace("_logs", "");
                    getOrCreateTagBuilder(tag)
                            .add(blockFamily.getBlock(name + "_log").get().asItem())
                            .add(blockFamily.getBlock(name + "_wood").get().asItem())
                            .add(blockFamily.getBlock("stripped_" + name + "_log").get().asItem())
                            .add(blockFamily.getBlock("stripped_" + name + "_wood").get().asItem())
                    ;
                    getOrCreateTagBuilder(ItemTags.LOGS_THAT_BURN).addTag(tag);
                }
                for (TagKey<Item> tag : blockFamily.LOG_ITEM_TAGS) {
                    String name = tag.location().getPath().replace("_logs", "");
                    getOrCreateTagBuilder(tag)
                            .add(blockFamily.getBlock(name + "_log").get().asItem())
                            .add(blockFamily.getBlock(name + "_wood").get().asItem())
                            .add(blockFamily.getBlock("stripped_" + name + "_log").get().asItem())
                            .add(blockFamily.getBlock("stripped_" + name + "_wood").get().asItem())
                    ;
                    getOrCreateTagBuilder(ItemTags.LOGS).addTag(tag);
                }
                for (Supplier<Block> block : blockFamily.LEAVES) {
                    getOrCreateTagBuilder(ItemTags.LEAVES).add(block.get().asItem());
                }
                for (Supplier<Block> block : blockFamily.FLOWERS) {
                    getOrCreateTagBuilder(ItemTags.FLOWERS).add(block.get().asItem());
                }
                for (Supplier<Block> block : blockFamily.SAPLINGS) {
                    getOrCreateTagBuilder(ItemTags.SAPLINGS).add(block.get().asItem());
                }
            }
        }
    }
}
