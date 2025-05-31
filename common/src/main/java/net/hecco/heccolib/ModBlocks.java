package net.hecco.heccolib;

import net.hecco.heccolib.platform.Services;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class ModBlocks {

    public static final Supplier<Block> TEST_BLOCK = Services.REGISTRIES.registerBlock(HeccoLib.MOD_ID, "test", () -> new TestBlock(BlockBehaviour.Properties.of()));

    public static void register() {

    }
}
