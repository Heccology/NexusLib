package net.hecco.heccolib.lib.blockFamilyGenerator;

import net.hecco.heccolib.lib.publicBlocks.*;
import net.hecco.heccolib.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class OldBlockFamilyGenerator {
    public final String MOD_ID;


    public static Map<String, OldBlockFamilyGenerator> BLOCK_FAMILIES = new HashMap<>();
    public static Map<String, Block> BLOCKS = new HashMap<>();
    public static Map<Block, Block> VARIANT_TO_BASE_BLOCK = new HashMap<>();

    public static final ArrayList<Block> CUBE_ALL = new ArrayList<>();
    public static final ArrayList<Block> STAIRS = new ArrayList<>();
    public static final ArrayList<Block> SLABS = new ArrayList<>();
    public static final ArrayList<Block> WALLS = new ArrayList<>();
    public static final ArrayList<Block> FENCES = new ArrayList<>();
    public static final ArrayList<Block> WOODEN_FENCES = new ArrayList<>();
    public static final ArrayList<Block> FENCE_GATES = new ArrayList<>();
    public static final ArrayList<Block> DOORS = new ArrayList<>();
    public static final ArrayList<Block> WOODEN_DOORS = new ArrayList<>();
    public static final ArrayList<Block> TRAPDOORS = new ArrayList<>();
    public static final ArrayList<Block> WOODEN_TRAPDOORS = new ArrayList<>();
    public static final ArrayList<Block> PRESSURE_PLATES = new ArrayList<>();
    public static final ArrayList<Block> WOODEN_PRESSURE_PLATES = new ArrayList<>();
    public static final ArrayList<Block> STONE_PRESSURE_PLATES = new ArrayList<>();
    public static final ArrayList<Block> BUTTONS = new ArrayList<>();
    public static final ArrayList<Block> WOODEN_BUTTONS = new ArrayList<>();
    public static final ArrayList<Block> STONE_BUTTONS = new ArrayList<>();
    public static final ArrayList<Block> LEAVES = new ArrayList<>();
    public static final ArrayList<Block> FLOWERS = new ArrayList<>();
    public static final ArrayList<Block> SAPLINGS = new ArrayList<>();
    public static final ArrayList<Block> FLOWER_POTS = new ArrayList<>();

    public static Map<Block, Block> LOGS_TO_WOODS = new HashMap<>();
    public static final ArrayList<TagKey<Block>> FLAMMABLE_LOG_TAGS = new ArrayList<>();
    public static final ArrayList<TagKey<Block>> LOG_TAGS = new ArrayList<>();
    public static final ArrayList<Block> OVERWORLD_NATURAL_LOGS = new ArrayList<>();

    public static final ArrayList<Block> PICKAXE_MINEABLE = new ArrayList<>();
    public static final ArrayList<Block> AXE_MINEABLE = new ArrayList<>();
    public static final ArrayList<Block> SHOVEL_MINEABLE = new ArrayList<>();
    public static final ArrayList<Block> HOE_MINEABLE = new ArrayList<>();
    public static final ArrayList<Block> NEEDS_STONE_TOOL = new ArrayList<>();
    public static final ArrayList<Block> NEEDS_IRON_TOOL = new ArrayList<>();
    public static final ArrayList<Block> NEEDS_DIAMOND_TOOL = new ArrayList<>();

    public static final ArrayList<Block> FOLIAGE_TINTED = new ArrayList<>();

    public static final HashMap<Block, Block> STRIPPABLE = new HashMap<>();

    public static final ArrayList<Block> CUSTOM_STAIRS_MODEL = new ArrayList<>();
    public static final ArrayList<Block> CUSTOM_SLAB_MODEL = new ArrayList<>();
    public static final ArrayList<Block> CUSTOM_WALL_MODEL = new ArrayList<>();

    private static Block registerBlock(String modId, String name, Mineables mineable, MinMiningToolTier minMiningToolTier, Supplier<Block> block, ArrayList<Block> blockFamilyBlocks) {
        Supplier<Block> block1 = Services.REGISTRIES.registerBlock(modId, name, block);
//        switch (mineable) {
//            case PICKAXE -> PICKAXE_MINEABLE.add(block1);
//            case AXE -> AXE_MINEABLE.add(block1);
//            case SHOVEL -> SHOVEL_MINEABLE.add(block1);
//            case HOE -> HOE_MINEABLE.add(block1);
//        }
//        switch (minMiningToolTier) {
//            case STONE -> NEEDS_STONE_TOOL.add(block1);
//            case IRON -> NEEDS_IRON_TOOL.add(block1);
//            case DIAMOND -> NEEDS_DIAMOND_TOOL.add(block1);
//        }
//        BLOCKS.put(name, block1);
//        blockFamilyBlocks.add(block1);
        return block1.get();
    }

    private static Block registerBlockNoItem(String modId, String name, Mineables mineable, MinMiningToolTier minMiningToolTier, Supplier<Block> block, ArrayList<Block> blockFamilyBlocks) {
        Block block1 = Services.REGISTRIES.registerBlock(modId, name, block).get();
        switch (mineable) {
            case PICKAXE -> PICKAXE_MINEABLE.add(block1);
            case AXE -> AXE_MINEABLE.add(block1);
            case SHOVEL -> SHOVEL_MINEABLE.add(block1);
            case HOE -> HOE_MINEABLE.add(block1);
        }
        switch (minMiningToolTier) {
            case STONE -> NEEDS_STONE_TOOL.add(block1);
            case IRON -> NEEDS_IRON_TOOL.add(block1);
            case DIAMOND -> NEEDS_DIAMOND_TOOL.add(block1);
        }
        BLOCKS.put(name, block1);
        blockFamilyBlocks.add(block1);
        return block1;
    }

    private final String name;
    private String prefix = "";
    private String suffix = "";
    private final Mineables mineable;
    private final MinMiningToolTier minMiningToolTier;
    private Block baseBlock;
    private final BlockBehaviour.Properties settings;
    private final ArrayList<Block> blockFamilyBlocks = new ArrayList<>();

    public OldBlockFamilyGenerator(String modId, String name, Mineables mineable, MinMiningToolTier minMiningToolTier, String baseBlockId, Supplier<Block> baseBlock, BlockBehaviour.Properties settings, boolean generateBaseBlockModel) {
        this.MOD_ID = modId;
        this.name = name;
        this.mineable = mineable;
        this.minMiningToolTier = minMiningToolTier;
        this.baseBlock = baseBlock.get();
        this.settings = settings;
        registerBlock(modId, baseBlockId, mineable, minMiningToolTier, baseBlock, blockFamilyBlocks);
        if (generateBaseBlockModel) {
            CUBE_ALL.add(baseBlock.get());
        }
        BLOCK_FAMILIES.put(name, this);
    }

    public OldBlockFamilyGenerator block(String prefix, String suffix, boolean generateModel) {
        Block block = registerBlock(MOD_ID, (prefix != "" ? prefix + "_" : "") + name + (suffix != "" ? "_" + suffix : ""), mineable, minMiningToolTier, () -> new Block(settings), blockFamilyBlocks);
        VARIANT_TO_BASE_BLOCK.put(block, baseBlock);
        this.baseBlock = block;

        this.prefix = prefix == "" ? "" : prefix + "_";
        this.suffix = suffix == "" ? "" : "_" + suffix;
        if (suffix == "bricks") {
            this.suffix = "_brick";
        } else if (suffix == "tiles") {
            this.suffix = "_tile";
        }

        if (generateModel) {
            CUBE_ALL.add(block);
        }
        return this;
    }

    public OldBlockFamilyGenerator block(String prefix, String suffix, Block block, boolean generateModel) {
        Block finalBlock = block;
        block = registerBlock(MOD_ID, (prefix != "" ? prefix + "_" : "") + name + (suffix != "" ? "_" + suffix : ""), mineable, minMiningToolTier, () -> finalBlock, blockFamilyBlocks);
        VARIANT_TO_BASE_BLOCK.put(block, baseBlock);
        this.baseBlock = block;

        this.prefix = prefix == "" ? "" : prefix + "_";
        this.suffix = suffix == "" ? "" : "_" + suffix;
        if (suffix == "bricks") {
            this.suffix = "_brick";
        } else if (suffix == "tiles") {
            this.suffix = "_tile";
        }

        if (generateModel) {
            CUBE_ALL.add(block);
        }
        return this;
    }

    public OldBlockFamilyGenerator stairs() {
        Block block = registerBlock(MOD_ID, prefix + name + suffix + "_stairs", mineable, minMiningToolTier, () -> new PublicStairBlock(baseBlock.defaultBlockState(), settings), blockFamilyBlocks);
        STAIRS.add(block);
        VARIANT_TO_BASE_BLOCK.put(block, baseBlock);

        return this;
    }

    public OldBlockFamilyGenerator stairs(boolean customModel) {
        Block block = registerBlock(MOD_ID, prefix + name + suffix + "_stairs", mineable, minMiningToolTier, () -> new PublicStairBlock(baseBlock.defaultBlockState(), settings), blockFamilyBlocks);
        STAIRS.add(block);
        if (customModel) {
            CUSTOM_STAIRS_MODEL.add(block);
        }
        VARIANT_TO_BASE_BLOCK.put(block, baseBlock);

        return this;
    }

    public OldBlockFamilyGenerator slab() {
        Block block = registerBlock(MOD_ID, prefix + name + suffix + "_slab", mineable, minMiningToolTier, () -> new SlabBlock(settings), blockFamilyBlocks);
        SLABS.add(block);
        VARIANT_TO_BASE_BLOCK.put(block, baseBlock);

        return this;
    }

    public OldBlockFamilyGenerator slab(boolean customModel) {
        Block block = registerBlock(MOD_ID, prefix + name + suffix + "_slab", mineable, minMiningToolTier, () -> new SlabBlock(settings), blockFamilyBlocks);
        SLABS.add(block);
        if (customModel) {
            CUSTOM_SLAB_MODEL.add(block);
        }
        VARIANT_TO_BASE_BLOCK.put(block, baseBlock);

        return this;
    }

    public OldBlockFamilyGenerator wall() {
        Block block = registerBlock(MOD_ID, prefix + name + suffix + "_wall", mineable, minMiningToolTier, () -> new WallBlock(settings), blockFamilyBlocks);
        WALLS.add(block);
        VARIANT_TO_BASE_BLOCK.put(block, baseBlock);

        return this;
    }

    public OldBlockFamilyGenerator wall(boolean customModel) {
        Block block = registerBlock(MOD_ID, prefix + name + suffix + "_wall", mineable, minMiningToolTier, () -> new WallBlock(settings), blockFamilyBlocks);
        WALLS.add(block);
        if (customModel) {
            CUSTOM_WALL_MODEL.add(block);
        }
        VARIANT_TO_BASE_BLOCK.put(block, baseBlock);

        return this;
    }

    public OldBlockFamilyGenerator fence(boolean wooden) {
        Block block = registerBlock(MOD_ID, name + "_fence", mineable, minMiningToolTier, () -> new FenceBlock(settings), blockFamilyBlocks);
        if (wooden) {
            WOODEN_FENCES.add(block);
        } else {
            FENCES.add(block);
        }
        VARIANT_TO_BASE_BLOCK.put(block, baseBlock);

        return this;
    }

    public OldBlockFamilyGenerator fenceGate(WoodType woodType) {
        Block block = registerBlock(MOD_ID, name + "_fence_gate", mineable, minMiningToolTier, () -> new FenceGateBlock(woodType, settings), blockFamilyBlocks);
        FENCE_GATES.add(block);
        VARIANT_TO_BASE_BLOCK.put(block, baseBlock);

        return this;
    }

    public OldBlockFamilyGenerator logs(boolean burnable, boolean overworld) {
        Block log = registerBlock(MOD_ID, name + "_log", mineable, minMiningToolTier, () -> new RotatedPillarBlock(settings), blockFamilyBlocks);

        Block strippedLog = registerBlock(MOD_ID, "stripped_" + name + "_log", mineable, minMiningToolTier, () -> new RotatedPillarBlock(settings), blockFamilyBlocks);

        Block wood = registerBlock(MOD_ID, name + "_wood", mineable, minMiningToolTier, () -> new RotatedPillarBlock(settings), blockFamilyBlocks);

        Block strippedWood = registerBlock(MOD_ID, "stripped_" + name + "_wood", mineable, minMiningToolTier, () -> new RotatedPillarBlock(settings), blockFamilyBlocks);

        LOGS_TO_WOODS.put(log, wood);
        LOGS_TO_WOODS.put(strippedLog, strippedWood);
        if (burnable) {
            FLAMMABLE_LOG_TAGS.add(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, this.name + "_logs")));
        } else {
            LOG_TAGS.add(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, this.name + "_logs")));
        }
        if (overworld) {
            OVERWORLD_NATURAL_LOGS.add(log);
        }

        STRIPPABLE.put(wood, strippedWood);
        STRIPPABLE.put(log, strippedLog);

        return this;
    }

    public OldBlockFamilyGenerator door(BlockSetType blockSetType, boolean wooden) {
        Block block = registerBlock(MOD_ID, name + "_door", mineable, minMiningToolTier, () -> new PublicDoorBlock(blockSetType, settings), blockFamilyBlocks);
        if (wooden) {
            WOODEN_DOORS.add(block);
        } else {
            DOORS.add(block);
        }

        VARIANT_TO_BASE_BLOCK.put(block, baseBlock);

        return this;
    }

    public OldBlockFamilyGenerator trapdoor(BlockSetType blockSetType, boolean wooden) {
        Block block = registerBlock(MOD_ID, name + "_trapdoor", mineable, minMiningToolTier, () -> new PublicTrapdoorBlock(blockSetType, settings.noOcclusion()), blockFamilyBlocks);
        if (wooden) {
            WOODEN_TRAPDOORS.add(block);
        } else {
            TRAPDOORS.add(block);
        }

        VARIANT_TO_BASE_BLOCK.put(block, baseBlock);

        return this;
    }

    public OldBlockFamilyGenerator pressurePlate(BlockSetType blockSetType, boolean wooden, boolean stone) {
        Block block = registerBlock(MOD_ID, name + "_pressure_plate", mineable, minMiningToolTier, () -> new PublicPressurePlateBlock(blockSetType, settings.forceSolidOn().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY)), blockFamilyBlocks);
        if (wooden) {
            WOODEN_PRESSURE_PLATES.add(block);
        } else if (stone) {
            STONE_PRESSURE_PLATES.add(block);
        } else {
            PRESSURE_PLATES.add(block);
        }
        VARIANT_TO_BASE_BLOCK.put(block, baseBlock);

        return this;
    }

    public OldBlockFamilyGenerator button(BlockSetType blockSetType, boolean wooden, boolean stone, int pressTicks) {
        Block block = registerBlock(MOD_ID, name + "_button", mineable, minMiningToolTier, () -> new PublicButtonBlock(blockSetType, pressTicks, BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY)), blockFamilyBlocks);
        if (wooden) {
            WOODEN_BUTTONS.add(block);
        } else if (stone) {
            STONE_BUTTONS.add(block);
        } else {
            BUTTONS.add(block);
        }
        VARIANT_TO_BASE_BLOCK.put(block, baseBlock);

        return this;
    }

    public OldBlockFamilyGenerator leaves(SoundType sounds, MapColor mapColor, boolean tint) {
        Block block = registerBlock(MOD_ID, name + "_leaves", Mineables.HOE, MinMiningToolTier.NONE, () -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).sound(sounds).mapColor(mapColor)), blockFamilyBlocks);
        LEAVES.add(block);
        if (tint) {
            FOLIAGE_TINTED.add(block);
        }

        return this;
    }

    public OldBlockFamilyGenerator leavesVariant(SoundType sounds, MapColor mapColor, String name, boolean flower, boolean tint) {
        Block block = registerBlock(MOD_ID, name + "_" + this.name + "_leaves", Mineables.HOE, MinMiningToolTier.NONE, () -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).sound(sounds).mapColor(mapColor)), blockFamilyBlocks);
        LEAVES.add(block);
        if (flower) {
            FLOWERS.add(block);
        }
        if (tint) {
            FOLIAGE_TINTED.add(block);
        }

        return this;
    }

    public OldBlockFamilyGenerator sapling(TreeGrower saplingGenerator) {
        Block block = registerBlock(MOD_ID, name + "_sapling", Mineables.NONE, MinMiningToolTier.NONE, () -> new PublicSaplingBlock(saplingGenerator, BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)), blockFamilyBlocks);
        Block pottedBlock = registerBlockNoItem(MOD_ID, "potted_" + name + "_sapling", Mineables.NONE, MinMiningToolTier.NONE, () -> new FlowerPotBlock(block, BlockBehaviour.Properties.of().noCollission().noOcclusion().pushReaction(PushReaction.DESTROY)), blockFamilyBlocks);
        SAPLINGS.add(block);
        FLOWER_POTS.add(pottedBlock);

        return this;
    }

    public Block getVariant(String variant) {
        if (!variant.isEmpty()) {
            for (Block block : blockFamilyBlocks) {
                if (BuiltInRegistries.BLOCK.getKey(block).getPath().contains(this.name)) {
                    String id = BuiltInRegistries.BLOCK.getKey(block).getPath().replace(this.name + "_", "").replace("_" + this.name, "");
                    if (id.equals(variant)) {
                        return block;
                    }
                }
            }
        } else {
            return baseBlock;
        }
        throw new RuntimeException("No such " + this.name + " block variant exists");
    }
}