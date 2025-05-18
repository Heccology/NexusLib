package net.hecco.heccolib.lib.blockFamilyGenerator;

import net.hecco.heccolib.lib.publicBlocks.PublicStairBlock;
import net.hecco.heccolib.platform.Services;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class BlockFamilyCreator {

    public static final Map<String, BlockFamilyCreator> BLOCK_FAMILIES = new HashMap<>();
    public static final Map<String, Supplier<Block>> BLOCKS = new HashMap<>();

    public final ArrayList<Supplier<Block>> STAIRS = new ArrayList<>();
    public final ArrayList<Supplier<Block>> SLABS = new ArrayList<>();
    public final ArrayList<Supplier<Block>> WALLS = new ArrayList<>();

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
        Supplier<Block> registeredBlock = Services.REGISTRIES.registerBlock(this.modId, name, block);
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
        return registeredBlock;
    }

    public BlockFamilyCreator block(String prefix, String suffix, String name, Mineables mineables, MinMiningToolTier minMiningToolTier, boolean generateModel) {
        this.mineables = mineables;
        this.generateModel = generateModel;
        this.minMiningToolTier = minMiningToolTier;
        createBlockType(prefix, suffix, name);
        return this;
    }

    public BlockFamilyCreator block(String prefix, String suffix, String name, Mineables mineables, boolean generateModel) {
        this.mineables = mineables;
        this.generateModel = generateModel;
        createBlockType(prefix, suffix, name);
        return this;
    }

    public BlockFamilyCreator block(String prefix, String suffix, String name, boolean generateModel) {
        this.generateModel = generateModel;
        createBlockType(prefix, suffix, name);
        return this;
    }

    private void createBlockType(String prefix, String suffix, String name) {
        Supplier<Block> block = registerBlock((prefix != "" ? prefix + "_" : "") + name + (suffix != "" ? "_" + suffix : ""), () -> new Block(BlockBehaviour.Properties.of()));
        this.prefix = prefix == "" ? "" : prefix + "_";
        this.suffix = suffix == "" ? "" : "_" + (suffix.substring(suffix.length() - 1) == "s" ? suffix.replace(suffix.substring(suffix.length()-1), "") : suffix);
        this.currentBlock = block;
        if (this.generateModel) {
            CUBE.add(block);
        }
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


    public Supplier<Block> getBlock(String name) {
        return BLOCKS.get(name);
    }
}
