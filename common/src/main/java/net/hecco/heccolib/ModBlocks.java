package net.hecco.heccolib;

import net.hecco.heccolib.lib.blockFamilyGenerator.BlockFamilyCreator;

public class ModBlocks {

    public static void register() {
        BlockFamilyCreator blockFamilyCreator = new BlockFamilyCreator("bountifulfares", "hectalite")
                .block("", "","hectalites", true).stairs().slab();
    }
}
