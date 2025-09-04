package net.hecco.heccolib;

import net.hecco.heccolib.lib.untintedParticleRegistry.HLUntintedParticleRegistry;
import net.minecraft.world.level.block.Blocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeccoLib {

    public static final String MOD_ID = "heccolib";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        HLUntintedParticleRegistry.add(Blocks.POTTED_FERN); //Fixed your game mojang hehahehaw
    }
}