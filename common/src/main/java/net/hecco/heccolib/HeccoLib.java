package net.hecco.heccolib;

import net.hecco.heccolib.lib.blockFamilyGenerator.BlockFamilyGenerator;
import net.hecco.heccolib.lib.blockFamilyGenerator.OldBlockFamilyGenerator;
import net.hecco.heccolib.lib.blockFamilyGenerator.MinMiningToolTier;
import net.hecco.heccolib.lib.blockFamilyGenerator.Mineables;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
public class HeccoLib {

    public static final String MOD_ID = "heccolib";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        ModBlocks.register();
    }
}