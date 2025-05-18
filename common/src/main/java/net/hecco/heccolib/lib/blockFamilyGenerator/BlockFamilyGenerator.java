package net.hecco.heccolib.lib.blockFamilyGenerator;

import net.hecco.heccolib.lib.publicBlocks.*;
import net.hecco.heccolib.platform.Services;
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
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class BlockFamilyGenerator {
    public static String MOD_ID;


    public static Map<String, BlockFamilyGenerator> BLOCK_FAMILIES = new HashMap<>();
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

    private Block registerBlock(String name, Mineables mineable, Supplier<Block> block) {
        Block block1 = Services.REGISTRIES.registerBlock(MOD_ID, name, block).get();
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

    private Block registerBlockNoItem(String name, Mineables mineable, Supplier<Block> block) {
        Block block1 = Services.REGISTRIES.registerBlock(MOD_ID, name, block).get();
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
    public BlockFamilyGenerator(String modId, String name, Mineables mineable, MinMiningToolTier minMiningToolTier, String baseBlockId, Supplier<Block> baseBlock, BlockBehaviour.Properties settings, boolean generateBaseBlockModel) {
        MOD_ID = modId;
        this.name = name;
        this.mineable = mineable;
        this.minMiningToolTier = minMiningToolTier;
        this.baseBlock = baseBlock.get();
        this.settings = settings;
        registerBlock(baseBlockId, mineable, baseBlock);
        if (generateBaseBlockModel) {
            CUBE_ALL.add(baseBlock.get());
        }
        BLOCK_FAMILIES.put(name, this);
    }

    public BlockFamilyGenerator block(String prefix, String suffix, boolean generateModel) {
        Block block = registerBlock((prefix != "" ? prefix + "_" : "") + name + (suffix != "" ? "_" + suffix : ""), mineable, () -> new Block(settings));
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

    public BlockFamilyGenerator block(String prefix, String suffix, Block block, boolean generateModel) {
        Block finalBlock = block;
        block = registerBlock((prefix != "" ? prefix + "_" : "") + name + (suffix != "" ? "_" + suffix : ""), mineable, () -> finalBlock);
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

    public BlockFamilyGenerator stairs() {
        Block block = registerBlock(prefix + name + suffix + "_stairs", mineable, () -> new PublicStairBlock(baseBlock.defaultBlockState(), settings));
        STAIRS.add(block);
        VARIANT_TO_BASE_BLOCK.put(block, baseBlock);

        return this;
    }

    public BlockFamilyGenerator stairs(boolean customModel) {
        Block block = registerBlock(prefix + name + suffix + "_stairs", mineable, () -> new PublicStairBlock(baseBlock.defaultBlockState(), settings));
        STAIRS.add(block);
        if (customModel) {
            CUSTOM_STAIRS_MODEL.add(block);
        }
        VARIANT_TO_BASE_BLOCK.put(block, baseBlock);

        return this;
    }

    public BlockFamilyGenerator slab() {
        Block block = registerBlock(prefix + name + suffix + "_slab", mineable, () -> new SlabBlock(settings));
        SLABS.add(block);
        VARIANT_TO_BASE_BLOCK.put(block, baseBlock);

        return this;
    }

    public BlockFamilyGenerator slab(boolean customModel) {
        Block block = registerBlock(prefix + name + suffix + "_slab", mineable, () -> new SlabBlock(settings));
        SLABS.add(block);
        if (customModel) {
            CUSTOM_SLAB_MODEL.add(block);
        }
        VARIANT_TO_BASE_BLOCK.put(block, baseBlock);

        return this;
    }

    public BlockFamilyGenerator wall() {
        Block block = registerBlock(prefix + name + suffix + "_wall", mineable, () -> new WallBlock(settings));
        WALLS.add(block);
        VARIANT_TO_BASE_BLOCK.put(block, baseBlock);

        return this;
    }

    public BlockFamilyGenerator wall(boolean customModel) {
        Block block = registerBlock(prefix + name + suffix + "_wall", mineable, () -> new WallBlock(settings));
        WALLS.add(block);
        if (customModel) {
            CUSTOM_WALL_MODEL.add(block);
        }
        VARIANT_TO_BASE_BLOCK.put(block, baseBlock);

        return this;
    }

    public BlockFamilyGenerator fence(boolean wooden) {
        Block block = registerBlock(name + "_fence", mineable, () -> new FenceBlock(settings));
        if (wooden) {
            WOODEN_FENCES.add(block);
        } else {
            FENCES.add(block);
        }
        VARIANT_TO_BASE_BLOCK.put(block, baseBlock);

        return this;
    }

    public BlockFamilyGenerator fenceGate(WoodType woodType) {
        Block block = registerBlock(name + "_fence_gate", mineable, () -> new FenceGateBlock(woodType, settings));
        FENCE_GATES.add(block);
        VARIANT_TO_BASE_BLOCK.put(block, baseBlock);

        return this;
    }

    public BlockFamilyGenerator logs(boolean burnable, boolean overworld) {
        Block log = registerBlock(name + "_log", mineable, () -> new RotatedPillarBlock(settings));

        Block strippedLog = registerBlock("stripped_" + name + "_log", mineable, () -> new RotatedPillarBlock(settings));

        Block wood = registerBlock(name + "_wood", mineable, () -> new RotatedPillarBlock(settings));

        Block strippedWood = registerBlock("stripped_" + name + "_wood", mineable, () -> new RotatedPillarBlock(settings));

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

    public BlockFamilyGenerator door(BlockSetType blockSetType, boolean wooden) {
        Block block = registerBlock(name + "_door", mineable, () -> new PublicDoorBlock(blockSetType, settings));
        if (wooden) {
            WOODEN_DOORS.add(block);
        } else {
            DOORS.add(block);
        }

        VARIANT_TO_BASE_BLOCK.put(block, baseBlock);

        return this;
    }

    public BlockFamilyGenerator trapdoor(BlockSetType blockSetType, boolean wooden) {
        Block block = registerBlock(name + "_trapdoor", mineable, () -> new PublicTrapdoorBlock(blockSetType, settings.noOcclusion()));
        if (wooden) {
            WOODEN_TRAPDOORS.add(block);
        } else {
            TRAPDOORS.add(block);
        }

        VARIANT_TO_BASE_BLOCK.put(block, baseBlock);

        return this;
    }

    public BlockFamilyGenerator pressurePlate(BlockSetType blockSetType, boolean wooden, boolean stone) {
        Block block = registerBlock(name + "_pressure_plate", mineable, () -> new PublicPressurePlateBlock(blockSetType, settings.forceSolidOn().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY)));
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

    public BlockFamilyGenerator button(BlockSetType blockSetType, boolean wooden, boolean stone, int pressTicks) {
        Block block = registerBlock(name + "_button", mineable, () -> new PublicButtonBlock(blockSetType, pressTicks, BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY)));
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

    public BlockFamilyGenerator leaves(SoundType sounds, MapColor mapColor, boolean tint) {
        Block block = registerBlock(name + "_leaves", Mineables.HOE, () -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).sound(sounds).mapColor(mapColor)));
        LEAVES.add(block);
        if (tint) {
            FOLIAGE_TINTED.add(block);
        }

        return this;
    }

    public BlockFamilyGenerator leavesVariant(SoundType sounds, MapColor mapColor, String name, boolean flower, boolean tint) {
        Block block = registerBlock(name + "_" + this.name + "_leaves", Mineables.HOE, () -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).sound(sounds).mapColor(mapColor)));
        LEAVES.add(block);
        if (flower) {
            FLOWERS.add(block);
        }
        if (tint) {
            FOLIAGE_TINTED.add(block);
        }

        return this;
    }

    public BlockFamilyGenerator sapling(TreeGrower saplingGenerator) {
        Block block = registerBlock(name + "_sapling", Mineables.NONE, () -> new PublicSaplingBlock(saplingGenerator, BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));
        Block pottedBlock = registerBlockNoItem("potted_" + name + "_sapling", Mineables.NONE, () -> new FlowerPotBlock(block, BlockBehaviour.Properties.of().noCollission().noOcclusion().pushReaction(PushReaction.DESTROY)));
        SAPLINGS.add(block);
        FLOWER_POTS.add(pottedBlock);

        return this;
    }
}