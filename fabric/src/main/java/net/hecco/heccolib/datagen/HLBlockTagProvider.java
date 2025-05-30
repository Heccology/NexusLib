package net.hecco.heccolib.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.hecco.heccolib.lib.blockFamilyCreator.BlockFamilyCreator;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public abstract class HLBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    private final String MOD_ID;

    public HLBlockTagProvider(String modId, FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
        this.MOD_ID = modId;
    }

    public void generateBlockFamilyTags() {
        for (BlockFamilyCreator blockFamily : BlockFamilyCreator.BLOCK_FAMILIES.values()) {
            if (blockFamily.isOfMod(this.MOD_ID)) {
                for (Supplier<Block> block : blockFamily.WALLS) {
                    getOrCreateTagBuilder(BlockTags.WALLS).add(block.get());
                }
                for (Supplier<Block> block : blockFamily.STAIRS) {
                    getOrCreateTagBuilder(BlockTags.STAIRS).add(block.get());
                }
                for (Supplier<Block> block : blockFamily.SLABS) {
                    getOrCreateTagBuilder(BlockTags.SLABS).add(block.get());
                }
                for (Supplier<Block> block : blockFamily.FENCES) {
                    getOrCreateTagBuilder(BlockTags.FENCES).add(block.get());
                }
                for (Supplier<Block> block : blockFamily.WOODEN_FENCES) {
                    getOrCreateTagBuilder(BlockTags.WOODEN_FENCES).add(block.get());
                }
                for (Supplier<Block> block : blockFamily.FENCE_GATES) {
                    getOrCreateTagBuilder(BlockTags.FENCE_GATES).add(block.get());
                }
                for (Supplier<Block> block : blockFamily.WOODEN_DOORS) {
                    getOrCreateTagBuilder(BlockTags.WOODEN_DOORS).add(block.get());
                }
                for (Supplier<Block> block : blockFamily.DOORS) {
                    getOrCreateTagBuilder(BlockTags.DOORS).add(block.get());
                }
                for (Supplier<Block> block : blockFamily.WOODEN_TRAPDOORS) {
                    getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS).add(block.get());
                }
                for (Supplier<Block> block : blockFamily.TRAPDOORS) {
                    getOrCreateTagBuilder(BlockTags.TRAPDOORS).add(block.get());
                }
                for (Supplier<Block> block : blockFamily.WOODEN_PRESSURE_PLATES) {
                    getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(block.get());
                }
                for (Supplier<Block> block : blockFamily.STONE_PRESSURE_PLATES) {
                    getOrCreateTagBuilder(BlockTags.STONE_PRESSURE_PLATES).add(block.get());
                }
                for (Supplier<Block> block : blockFamily.PRESSURE_PLATES) {
                    getOrCreateTagBuilder(BlockTags.PRESSURE_PLATES).add(block.get());
                }
                for (Supplier<Block> block : blockFamily.WOODEN_PRESSURE_PLATES) {
                    getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(block.get());
                }
                for (Supplier<Block> block : blockFamily.STONE_PRESSURE_PLATES) {
                    getOrCreateTagBuilder(BlockTags.STONE_PRESSURE_PLATES).add(block.get());
                }
                for (Supplier<Block> block : blockFamily.PRESSURE_PLATES) {
                    getOrCreateTagBuilder(BlockTags.PRESSURE_PLATES).add(block.get());
                }
                for (Supplier<Block> block : blockFamily.WOODEN_BUTTONS) {
                    getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS).add(block.get());
                }
                for (Supplier<Block> block : blockFamily.STONE_BUTTONS) {
                    getOrCreateTagBuilder(BlockTags.STONE_BUTTONS).add(block.get());
                }
                for (Supplier<Block> block : blockFamily.BUTTONS) {
                    getOrCreateTagBuilder(BlockTags.BUTTONS).add(block.get());
                }
                for (TagKey<Block> tag : blockFamily.FLAMMABLE_LOG_TAGS) {
                    String name = tag.location().getPath().replace("_logs", "");
                    getOrCreateTagBuilder(tag)
                            .add(blockFamily.getBlock(name + "_log").get())
                            .add(blockFamily.getBlock(name + "_wood").get())
                            .add(blockFamily.getBlock("stripped_" + name + "_log").get())
                            .add(blockFamily.getBlock("stripped_" + name + "_wood").get())
                    ;
                    getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).addTag(tag);
                }
                for (TagKey<Block> tag : blockFamily.LOG_TAGS) {
                    String name = tag.location().getPath().replace("_logs", "");
                    getOrCreateTagBuilder(tag)
                            .add(blockFamily.getBlock(name + "_log").get())
                            .add(blockFamily.getBlock(name + "_wood").get())
                            .add(blockFamily.getBlock("stripped_" + name + "_log").get())
                            .add(blockFamily.getBlock("stripped_" + name + "_wood").get())
                    ;
                    getOrCreateTagBuilder(BlockTags.LOGS).addTag(tag);
                }
                for (Supplier<Block> block : blockFamily.OVERWORLD_NATURAL_LOGS) {
                    getOrCreateTagBuilder(BlockTags.OVERWORLD_NATURAL_LOGS).add(block.get());
                }
                for (Supplier<Block> block : blockFamily.LEAVES) {
                    getOrCreateTagBuilder(BlockTags.LEAVES).add(block.get());
                }
                for (Supplier<Block> block : blockFamily.FLOWERS) {
                    getOrCreateTagBuilder(BlockTags.FLOWERS).add(block.get());
                }
                for (Supplier<Block> block : blockFamily.SAPLINGS) {
                    getOrCreateTagBuilder(BlockTags.SAPLINGS).add(block.get());
                }
                for (Supplier<Block> block : blockFamily.FLOWER_POTS) {
                    getOrCreateTagBuilder(BlockTags.FLOWER_POTS).add(block.get());
                }


                for (Supplier<Block> block : blockFamily.PICKAXE_MINEABLE) {
                    getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE).add(block.get());
                }
                for (Supplier<Block> block : blockFamily.AXE_MINEABLE) {
                    getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE).add(block.get());
                }
                for (Supplier<Block> block : blockFamily.SHOVEL_MINEABLE) {
                    getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_SHOVEL).add(block.get());
                }
                for (Supplier<Block> block : blockFamily.HOE_MINEABLE) {
                    getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_HOE).add(block.get());
                }
                for (Supplier<Block> block : blockFamily.NEEDS_STONE_TOOL) {
                    getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(block.get());
                }
                for (Supplier<Block> block : blockFamily.NEEDS_IRON_TOOL) {
                    getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL).add(block.get());
                }
                for (Supplier<Block> block : blockFamily.NEEDS_DIAMOND_TOOL) {
                    getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL).add(block.get());
                }
            }
        }
    }
}
