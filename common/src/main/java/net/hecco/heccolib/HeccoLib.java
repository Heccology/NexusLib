package net.hecco.heccolib;

import net.hecco.heccolib.platform.Services;
import net.hecco.heccolib.platform.services.HLRegistryHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
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
        Services.REGISTRIES.registerBlock(MOD_ID, "test_block", () -> new Block(BlockBehaviour.Properties.of()));
    }
}