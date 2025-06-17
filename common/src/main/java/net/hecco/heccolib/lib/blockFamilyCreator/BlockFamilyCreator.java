package net.hecco.heccolib.lib.blockFamilyCreator;

import net.hecco.heccolib.lib.publicBlocks.*;
import net.hecco.heccolib.platform.HLServices;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class BlockFamilyCreator {

    public static final Map<String, BlockFamilyCreator> BLOCK_FAMILIES = new HashMap<>();
    public static final Map<String, Supplier<Block>> BLOCKS = new HashMap<>();

    public final Map<Supplier<Block>, Supplier<Block>> VARIANT_TO_BASE_BLOCK = new HashMap<>();
    public final ArrayList<Supplier<Block>> STAIRS = new ArrayList<>();
    public final ArrayList<Supplier<Block>> SLABS = new ArrayList<>();
    public final ArrayList<Supplier<Block>> WALLS = new ArrayList<>();
    public final ArrayList<Supplier<Block>> FENCES = new ArrayList<>();
    public final ArrayList<Supplier<Block>> WOODEN_FENCES = new ArrayList<>();
    public final ArrayList<Supplier<Block>> FENCE_GATES = new ArrayList<>();
    public final ArrayList<Supplier<Block>> DOORS = new ArrayList<>();
    public final ArrayList<Supplier<Block>> WOODEN_DOORS = new ArrayList<>();
    public final ArrayList<Supplier<Block>> TRAPDOORS = new ArrayList<>();
    public final ArrayList<Supplier<Block>> WOODEN_TRAPDOORS = new ArrayList<>();
    public final ArrayList<Supplier<Block>> PRESSURE_PLATES = new ArrayList<>();
    public final ArrayList<Supplier<Block>> WOODEN_PRESSURE_PLATES = new ArrayList<>();
    public final ArrayList<Supplier<Block>> STONE_PRESSURE_PLATES = new ArrayList<>();
    public final ArrayList<Supplier<Block>> BUTTONS = new ArrayList<>();
    public final ArrayList<Supplier<Block>> WOODEN_BUTTONS = new ArrayList<>();
    public final ArrayList<Supplier<Block>> STONE_BUTTONS = new ArrayList<>();
    public final ArrayList<Supplier<Block>> LOGS = new ArrayList<>();
    public final ArrayList<Supplier<Block>> LEAVES = new ArrayList<>();
    public final ArrayList<Supplier<Block>> FLOWERS = new ArrayList<>();
    public final ArrayList<Supplier<Block>> SAPLINGS = new ArrayList<>();
    public final ArrayList<Supplier<Block>> FLOWER_POTS = new ArrayList<>();
    public final ArrayList<Supplier<Block>> PLANKS = new ArrayList<>();
    public final ArrayList<TagKey<Block>> FLAMMABLE_LOG_TAGS = new ArrayList<>();
    public final ArrayList<TagKey<Item>> FLAMMABLE_LOG_ITEM_TAGS = new ArrayList<>();
    public final ArrayList<TagKey<Block>> LOG_TAGS = new ArrayList<>();
    public final ArrayList<TagKey<Item>> LOG_ITEM_TAGS = new ArrayList<>();
    public final ArrayList<Supplier<Block>> OVERWORLD_NATURAL_LOGS = new ArrayList<>();
    public final HashMap<Supplier<Block>, Supplier<Block>> STRIPPABLE = new HashMap<>();
    public final ArrayList<Supplier<Block>> FOLIAGE_TINTED = new ArrayList<>();

    public final ArrayList<Supplier<Block>> CUBE = new ArrayList<>();
    public final ArrayList<Supplier<Block>> STAIRS_MODEL = new ArrayList<>();
    public final ArrayList<Supplier<Block>> SLAB_MODEL = new ArrayList<>();
    public final ArrayList<Supplier<Block>> WALL_MODEL = new ArrayList<>();

    public final ArrayList<Supplier<Block>> PICKAXE_MINEABLE = new ArrayList<>();
    public final ArrayList<Supplier<Block>> AXE_MINEABLE = new ArrayList<>();
    public final ArrayList<Supplier<Block>> SHOVEL_MINEABLE = new ArrayList<>();
    public final ArrayList<Supplier<Block>> HOE_MINEABLE = new ArrayList<>();
    public final ArrayList<Supplier<Block>> NEEDS_STONE_TOOL = new ArrayList<>();
    public final ArrayList<Supplier<Block>> NEEDS_IRON_TOOL = new ArrayList<>();
    public final ArrayList<Supplier<Block>> NEEDS_DIAMOND_TOOL = new ArrayList<>();

    private String modId;
    private String name;
    private String prefix = "";
    private String suffix = "";
    private Mineables mineables = Mineables.NONE;
    private MinMiningToolTier minMiningToolTier = MinMiningToolTier.NONE;

    private Supplier<Block> currentBlock;
    private boolean generateModel = true;


    public final Map<String, Supplier<Block>> blockFamilyBlocks = new HashMap<>();

    public BlockFamilyCreator(String modId, String name) {
        this.modId = modId;
        this.name = name;
        BLOCK_FAMILIES.put(name, this);
    }

    public boolean isOfMod(String modId) {
        return this.modId == modId;
    }

    public Supplier<Block> registerBlock(String name, Supplier<Block> block) {
        Supplier<Block> registeredBlock = HLServices.REGISTRY.registerBlock(this.modId, name, block);
        BLOCKS.put(name, registeredBlock);
        this.blockFamilyBlocks.put(name, registeredBlock);
        switch (this.mineables) {
            case PICKAXE -> PICKAXE_MINEABLE.add(registeredBlock);
            case AXE -> AXE_MINEABLE.add(registeredBlock);
            case SHOVEL -> SHOVEL_MINEABLE.add(registeredBlock);
            case HOE -> HOE_MINEABLE.add(registeredBlock);
        }
        switch (this.minMiningToolTier) {
            case STONE -> NEEDS_STONE_TOOL.add(registeredBlock);
            case IRON -> NEEDS_IRON_TOOL.add(registeredBlock);
            case DIAMOND -> NEEDS_DIAMOND_TOOL.add(registeredBlock);
        }
        if (registeredBlock != currentBlock) {
            VARIANT_TO_BASE_BLOCK.put(registeredBlock, currentBlock);
        }
        return registeredBlock;
    }

    public Supplier<Block> registerBlock(String name, Mineables mineables, MinMiningToolTier minMiningToolTier, Supplier<Block> block) {
        Supplier<Block> registeredBlock = HLServices.REGISTRY.registerBlock(this.modId, name, block);
        BLOCKS.put(name, registeredBlock);
        this.blockFamilyBlocks.put(name, registeredBlock);
        switch (mineables) {
            case PICKAXE -> PICKAXE_MINEABLE.add(registeredBlock);
            case AXE -> AXE_MINEABLE.add(registeredBlock);
            case SHOVEL -> SHOVEL_MINEABLE.add(registeredBlock);
            case HOE -> HOE_MINEABLE.add(registeredBlock);
        }
        switch (minMiningToolTier) {
            case STONE -> NEEDS_STONE_TOOL.add(registeredBlock);
            case IRON -> NEEDS_IRON_TOOL.add(registeredBlock);
            case DIAMOND -> NEEDS_DIAMOND_TOOL.add(registeredBlock);
        }
        return registeredBlock;
    }

    public BlockFamilyCreator block(String prefix, String suffix, String baseBlockId, Mineables mineables, MinMiningToolTier minMiningToolTier, boolean generateModel) {
        this.mineables = mineables;
        this.generateModel = generateModel;
        this.minMiningToolTier = minMiningToolTier;
        createBlockType(prefix, suffix, baseBlockId);
        return this;
    }

    public BlockFamilyCreator block(String prefix, String suffix, String baseBlockId, Mineables mineables, boolean generateModel) {
        this.mineables = mineables;
        this.generateModel = generateModel;
        createBlockType(prefix, suffix, baseBlockId);
        return this;
    }

    public BlockFamilyCreator block(String prefix, String suffix, String baseBlockId, boolean generateModel) {
        this.generateModel = generateModel;
        createBlockType(prefix, suffix, baseBlockId);
        return this;
    }

    public BlockFamilyCreator planks() {
        this.generateModel = true;
        Supplier<Block> block =createBlockType("", "planks", this.name);
        updateAffixes("", "");
        this.PLANKS.add(block);
        return this;
    }

    private Supplier<Block> createBlockType(String prefix, String suffix, String name) {
        Supplier<Block> block = registerBlock((prefix != "" ? prefix + "_" : "") + name + (suffix != "" ? "_" + suffix : ""), () -> new Block(BlockBehaviour.Properties.of()));
        this.prefix = prefix == "" ? "" : prefix + "_";
        this.suffix = suffix == "" ? "" : "_" + (suffix.substring(suffix.length() - 1) == "s" ? suffix.replace(suffix.substring(suffix.length()-1), "") : suffix);
        this.currentBlock = block;
        if (this.generateModel) {
            CUBE.add(block);
        }
        return block;
    }

    public BlockFamilyCreator updateAffixes(String prefix, String suffix) {
        this.prefix = prefix == "" ? "" : prefix + "_";
        this.suffix = suffix == "" ? "" : "_" + (suffix.substring(suffix.length()-1) == "s" ? suffix.replace(suffix.substring(suffix.length()-1), "") : suffix);
        return this;
    }


    public BlockFamilyCreator stairs() {
        Supplier<Block> block = registerBlock(prefix + name + suffix + "_stairs", () -> new PublicStairBlock(this.currentBlock.get().defaultBlockState(), BlockBehaviour.Properties.of()));
        STAIRS.add(block);
        if (this.generateModel) {
            STAIRS_MODEL.add(block);
        }
        return this;
    }

    public BlockFamilyCreator slab() {
        Supplier<Block> block = registerBlock(prefix + name + suffix + "_slab", () -> new SlabBlock(BlockBehaviour.Properties.of()));
        SLABS.add(block);
        if (this.generateModel) {
            SLAB_MODEL.add(block);
        }
        return this;
    }

    public BlockFamilyCreator wall() {
        Supplier<Block> block = registerBlock(prefix + name + suffix + "_wall", () -> new WallBlock(BlockBehaviour.Properties.of()));
        WALLS.add(block);
        if (this.generateModel) {
            WALL_MODEL.add(block);
        }
        return this;
    }

    public BlockFamilyCreator fence(boolean wooden) {
        Supplier<Block> block = registerBlock(name + "_fence", () -> new FenceBlock(BlockBehaviour.Properties.of()));
        if (wooden) {
            WOODEN_FENCES.add(block);
        } else {
            FENCES.add(block);
        }
        return this;
    }

    public BlockFamilyCreator fenceGate(WoodType woodType) {
        Supplier<Block> block = registerBlock(name + "_fence_gate", () -> new FenceGateBlock(woodType, BlockBehaviour.Properties.of()));
        FENCE_GATES.add(block);

        return this;
    }

    public BlockFamilyCreator logs(boolean burnable, boolean overworld) {
        Supplier<Block> log = registerBlock(name + "_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of()));

        Supplier<Block> strippedLog = registerBlock("stripped_" + name + "_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of()));

        Supplier<Block> wood = registerBlock(name + "_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of()));

        Supplier<Block> strippedWood = registerBlock("stripped_" + name + "_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of()));

        if (burnable) {
            FLAMMABLE_LOG_TAGS.add(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(this.modId, this.name + "_logs")));
            FLAMMABLE_LOG_ITEM_TAGS.add(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(this.modId, this.name + "_logs")));
        } else {
            LOG_TAGS.add(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(this.modId, this.name + "_logs")));
            LOG_ITEM_TAGS.add(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(this.modId, this.name + "_logs")));
        }
        if (overworld) {
            OVERWORLD_NATURAL_LOGS.add(log);
        }
        LOGS.add(log);
        STRIPPABLE.put(wood, strippedWood);
        STRIPPABLE.put(log, strippedLog);

        return this;
    }

    public BlockFamilyCreator door(BlockSetType blockSetType, boolean wooden) {
        Supplier<Block> block = registerBlock(name + "_door", () -> new PublicDoorBlock(blockSetType, BlockBehaviour.Properties.of()));
        if (wooden) {
            WOODEN_DOORS.add(block);
        } else {
            DOORS.add(block);
        }
        return this;
    }

    public BlockFamilyCreator trapdoor(BlockSetType blockSetType, boolean wooden) {
        Supplier<Block> block = registerBlock(name + "_trapdoor", () -> new PublicTrapdoorBlock(blockSetType, BlockBehaviour.Properties.of().noOcclusion()));
        if (wooden) {
            WOODEN_TRAPDOORS.add(block);
        } else {
            TRAPDOORS.add(block);
        }
        return this;
    }

    public BlockFamilyCreator pressurePlate(BlockSetType blockSetType, boolean wooden, boolean stone) {
        Supplier<Block> block = registerBlock(name + "_pressure_plate", () -> new PublicPressurePlateBlock(blockSetType, BlockBehaviour.Properties.of().forceSolidOn().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY)));
        if (wooden) {
            WOODEN_PRESSURE_PLATES.add(block);
        } else if (stone) {
            STONE_PRESSURE_PLATES.add(block);
        } else {
            PRESSURE_PLATES.add(block);
        }
        return this;
    }

    public BlockFamilyCreator button(BlockSetType blockSetType, boolean wooden, boolean stone, int pressTicks) {
        Supplier<Block> block = registerBlock(name + "_button", () -> new PublicButtonBlock(blockSetType, pressTicks, BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY)));
        if (wooden) {
            WOODEN_BUTTONS.add(block);
        } else if (stone) {
            STONE_BUTTONS.add(block);
        } else {
            BUTTONS.add(block);
        }
        return this;
    }

    public BlockFamilyCreator leaves(SoundType sounds, boolean tint) {
        Supplier<Block> block = registerBlock(name + "_leaves", Mineables.HOE, MinMiningToolTier.NONE, () -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).sound(sounds)));
        LEAVES.add(block);
        if (tint) {
            FOLIAGE_TINTED.add(block);
        }
        return this;
    }

    public BlockFamilyCreator leaves(SoundType sounds, MapColor mapColor, String name, boolean flowering, boolean tint) {
        Supplier<Block> block = registerBlock(name + "_" + this.name + "_leaves", Mineables.HOE, MinMiningToolTier.NONE, () -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).sound(sounds).mapColor(mapColor)));
        LEAVES.add(block);
        if (flowering) {
            FLOWERS.add(block);
        }
        if (tint) {
            FOLIAGE_TINTED.add(block);
        }
        return this;
    }


    public BlockFamilyCreator addExistingBlock(Supplier<Block> block) {
        String i = BuiltInRegistries.BLOCK.getKey(block.get()).getPath();
        BLOCKS.put(i, block);
        this.blockFamilyBlocks.put(i, block);
        VARIANT_TO_BASE_BLOCK.put(block, currentBlock);
        return this;
    }

    public BlockFamilyCreator addExistingBlock(Supplier<Block> block, Mineables mineables, MinMiningToolTier minMiningToolTier) {
        String i = BuiltInRegistries.BLOCK.getKey(block.get()).getPath();
        BLOCKS.put(i, block);
        this.blockFamilyBlocks.put(i, block);
        switch (mineables) {
            case PICKAXE -> PICKAXE_MINEABLE.add(block);
            case AXE -> AXE_MINEABLE.add(block);
            case SHOVEL -> SHOVEL_MINEABLE.add(block);
            case HOE -> HOE_MINEABLE.add(block);
        }
        switch (minMiningToolTier) {
            case STONE -> NEEDS_STONE_TOOL.add(block);
            case IRON -> NEEDS_IRON_TOOL.add(block);
            case DIAMOND -> NEEDS_DIAMOND_TOOL.add(block);
        }
        VARIANT_TO_BASE_BLOCK.put(block, currentBlock);
        return this;
    }


    public Supplier<Block> getBlock(String name) {
        return BLOCKS.get(name);
    }
}
