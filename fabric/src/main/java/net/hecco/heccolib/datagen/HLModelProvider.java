package net.hecco.heccolib.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.hecco.heccolib.lib.blockFamilyCreator.BlockFamilyCreator;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import org.apache.commons.lang3.ArrayUtils;

import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class HLModelProvider extends FabricModelProvider {

    private final String MOD_ID;

    public HLModelProvider(String modId, FabricDataOutput output) {
        super(output);
        this.MOD_ID = modId;
    }

    public void generateBlockFamilyModels(BlockModelGenerators blockStateModelGenerator) {
        for (BlockFamilyCreator blockFamily : BlockFamilyCreator.BLOCK_FAMILIES.values()) {
            if (blockFamily.isOfMod(this.MOD_ID)) {
                for (Supplier<Block> block : blockFamily.CUBE) {
                    blockStateModelGenerator.createTrivialCube(block.get());
                }
                for (Supplier<Block> block : blockFamily.STAIRS) {
                    if (blockFamily.STAIRS_MODEL.contains(block)) {
                        stairs(blockStateModelGenerator, block.get(), blockFamily.VARIANT_TO_BASE_BLOCK.get(block).get());
                    }
                }
                for (Supplier<Block> block : blockFamily.SLABS) {
                    if (blockFamily.SLAB_MODEL.contains(block)) {
                        slab(blockStateModelGenerator, block.get(), blockFamily.VARIANT_TO_BASE_BLOCK.get(block).get());
                    }
                }
                for (Supplier<Block> block : blockFamily.WALLS) {
                    if (blockFamily.WALL_MODEL.contains(block)) {
                        wall(blockStateModelGenerator, block.get(), blockFamily.VARIANT_TO_BASE_BLOCK.get(block).get());
                    }
                }
                for (Supplier<Block> block : Stream.concat(blockFamily.WOODEN_FENCES.stream(), blockFamily.FENCES.stream()).toList()) {
                    fence(blockStateModelGenerator, block.get(), blockFamily.VARIANT_TO_BASE_BLOCK.get(block).get());
                }
                for (Supplier<Block> block : blockFamily.FENCE_GATES) {
                    fenceGate(blockStateModelGenerator, block.get(), blockFamily.VARIANT_TO_BASE_BLOCK.get(block).get());
                }
                for (Supplier<Block> block : blockFamily.LOGS) {
                    blockStateModelGenerator.woodProvider(block.get()).log(block.get()).wood(blockFamily.getBlock(BuiltInRegistries.BLOCK.getKey(block.get()).getPath().replace("log", "wood")).get());
                    blockStateModelGenerator.woodProvider(blockFamily.STRIPPABLE.get(block).get()).log(blockFamily.STRIPPABLE.get(block).get()).wood(blockFamily.STRIPPABLE.get(blockFamily.getBlock(BuiltInRegistries.BLOCK.getKey(block.get()).getPath().replace("log", "wood"))).get());
                }
                for (Supplier<Block> block : Stream.concat(blockFamily.WOODEN_DOORS.stream(), blockFamily.DOORS.stream()).toList()) {
                    blockStateModelGenerator.createDoor(block.get());
                }
                for (Supplier<Block> block : Stream.concat(blockFamily.WOODEN_TRAPDOORS.stream(), blockFamily.TRAPDOORS.stream()).toList()) {
                    blockStateModelGenerator.createOrientableTrapdoor(block.get());
                }
                for (Supplier<Block> block : Stream.concat(Stream.concat(blockFamily.WOODEN_PRESSURE_PLATES.stream(), blockFamily.STONE_PRESSURE_PLATES.stream()), blockFamily.PRESSURE_PLATES.stream()).toList()) {
                    pressurePlate(blockStateModelGenerator, block.get(), blockFamily.VARIANT_TO_BASE_BLOCK.get(block).get());
                }
                for (Supplier<Block> block : Stream.concat(Stream.concat(blockFamily.WOODEN_BUTTONS.stream(), blockFamily.STONE_BUTTONS.stream()), blockFamily.BUTTONS.stream()).toList()) {
                    button(blockStateModelGenerator, block.get(), blockFamily.VARIANT_TO_BASE_BLOCK.get(block).get());
                }
                for (Supplier<Block> block : blockFamily.LEAVES) {
                    blockStateModelGenerator.createTrivialBlock(block.get(), TexturedModel.LEAVES);
                }
                for (Supplier<Block> block : blockFamily.FLOWER_POTS) {
                    blockStateModelGenerator.createPlant(((FlowerPotBlock) block.get()).getPotted(), block.get(), BlockModelGenerators.TintState.NOT_TINTED);
                }
            }
        }
    }

    private void stairs(BlockModelGenerators blockStateModelGenerator, Block block, Block baseBlock) {
        TexturedModel texturedModel = TexturedModel.CUBE.get(baseBlock);
        TextureMapping textures = texturedModel.getMapping();
        ResourceLocation identifier = ModelTemplates.STAIRS_INNER.create(block, textures, blockStateModelGenerator.modelOutput);
        ResourceLocation identifier2 = ModelTemplates.STAIRS_STRAIGHT.create(block, textures, blockStateModelGenerator.modelOutput);
        ResourceLocation identifier3 = ModelTemplates.STAIRS_OUTER.create(block, textures, blockStateModelGenerator.modelOutput);
        blockStateModelGenerator.blockStateOutput.accept(BlockModelGenerators.createStairs(block, identifier, identifier2, identifier3));
        blockStateModelGenerator.delegateItemModel(block, identifier2);
    }

    private void slab(BlockModelGenerators blockStateModelGenerator, Block block, Block baseBlock) {
        TexturedModel texturedModel = TexturedModel.CUBE.get(baseBlock);
        TextureMapping textures = texturedModel.getMapping();
        ResourceLocation identifier = ModelTemplates.SLAB_BOTTOM.create(block, textures, blockStateModelGenerator.modelOutput);
        ResourceLocation identifier2 = ModelTemplates.SLAB_TOP.create(block, textures, blockStateModelGenerator.modelOutput);
        blockStateModelGenerator.blockStateOutput.accept(BlockModelGenerators.createSlab(block, identifier, identifier2, ResourceLocation.fromNamespaceAndPath(BuiltInRegistries.BLOCK.getKey(baseBlock).getNamespace(), "block/" + BuiltInRegistries.BLOCK.getKey(baseBlock).getPath())));
        blockStateModelGenerator.delegateItemModel(block, identifier);
    }

    private void wall(BlockModelGenerators blockStateModelGenerator, Block block, Block baseBlock) {
        TexturedModel texturedModel = TexturedModel.CUBE.get(baseBlock);
        TextureMapping textures = texturedModel.getMapping();
        ResourceLocation identifier = ModelTemplates.WALL_POST.create(block, textures, blockStateModelGenerator.modelOutput);
        ResourceLocation identifier2 = ModelTemplates.WALL_LOW_SIDE.create(block, textures, blockStateModelGenerator.modelOutput);
        ResourceLocation identifier3 = ModelTemplates.WALL_TALL_SIDE.create(block, textures, blockStateModelGenerator.modelOutput);
        blockStateModelGenerator.blockStateOutput.accept(BlockModelGenerators.createWall(block, identifier, identifier2, identifier3));
        ResourceLocation identifier4 = ModelTemplates.WALL_INVENTORY.create(block, textures, blockStateModelGenerator.modelOutput);
        blockStateModelGenerator.delegateItemModel(block, identifier4);
    }

    private void fence(BlockModelGenerators blockStateModelGenerator, Block fenceBlock, Block baseBlock) {
        TexturedModel texturedModel = TexturedModel.CUBE.get(baseBlock);
        TextureMapping textures = texturedModel.getMapping();
        ResourceLocation identifier = ModelTemplates.FENCE_POST.create(fenceBlock, textures, blockStateModelGenerator.modelOutput);
        ResourceLocation identifier2 = ModelTemplates.FENCE_SIDE.create(fenceBlock, textures, blockStateModelGenerator.modelOutput);
        blockStateModelGenerator.blockStateOutput.accept(BlockModelGenerators.createFence(fenceBlock, identifier, identifier2));
        ResourceLocation identifier3 = ModelTemplates.FENCE_INVENTORY.create(fenceBlock, textures, blockStateModelGenerator.modelOutput);
        blockStateModelGenerator.delegateItemModel(fenceBlock, identifier3);
    }

    private void fenceGate(BlockModelGenerators blockStateModelGenerator, Block fenceGateBlock, Block baseBlock) {
        TexturedModel texturedModel = TexturedModel.CUBE.get(baseBlock);
        TextureMapping textures = texturedModel.getMapping();
        ResourceLocation identifier = ModelTemplates.FENCE_GATE_OPEN.create(fenceGateBlock, textures, blockStateModelGenerator.modelOutput);
        ResourceLocation identifier2 = ModelTemplates.FENCE_GATE_CLOSED.create(fenceGateBlock, textures, blockStateModelGenerator.modelOutput);
        ResourceLocation identifier3 = ModelTemplates.FENCE_GATE_WALL_OPEN.create(fenceGateBlock, textures, blockStateModelGenerator.modelOutput);
        ResourceLocation identifier4 = ModelTemplates.FENCE_GATE_WALL_CLOSED.create(fenceGateBlock, textures, blockStateModelGenerator.modelOutput);
        blockStateModelGenerator.blockStateOutput.accept(BlockModelGenerators.createFenceGate(fenceGateBlock, identifier, identifier2, identifier3, identifier4, true));
    }

    private void pressurePlate(BlockModelGenerators blockStateModelGenerator, Block pressurePlateBlock, Block baseBlock) {
        TexturedModel texturedModel = TexturedModel.CUBE.get(baseBlock);
        TextureMapping textures = texturedModel.getMapping();
        ResourceLocation identifier = ModelTemplates.PRESSURE_PLATE_UP.create(pressurePlateBlock, textures, blockStateModelGenerator.modelOutput);
        ResourceLocation identifier2 = ModelTemplates.PRESSURE_PLATE_DOWN.create(pressurePlateBlock, textures, blockStateModelGenerator.modelOutput);
        blockStateModelGenerator.blockStateOutput.accept(BlockModelGenerators.createPressurePlate(pressurePlateBlock, identifier, identifier2));
    }

    private void button(BlockModelGenerators blockStateModelGenerator, Block buttonBlock, Block baseBlock) {
        TexturedModel texturedModel = TexturedModel.CUBE.get(baseBlock);
        TextureMapping textures = texturedModel.getMapping();
        ResourceLocation identifier = ModelTemplates.BUTTON.create(buttonBlock, textures, blockStateModelGenerator.modelOutput);
        ResourceLocation identifier2 = ModelTemplates.BUTTON_PRESSED.create(buttonBlock, textures, blockStateModelGenerator.modelOutput);
        blockStateModelGenerator.blockStateOutput.accept(BlockModelGenerators.createButton(buttonBlock, identifier, identifier2));
        ResourceLocation identifier3 = ModelTemplates.BUTTON_INVENTORY.create(buttonBlock, textures, blockStateModelGenerator.modelOutput);
        blockStateModelGenerator.delegateItemModel(buttonBlock, identifier3);
    }
}
