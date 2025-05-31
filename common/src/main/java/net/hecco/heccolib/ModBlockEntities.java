package net.hecco.heccolib;

import net.hecco.heccolib.platform.Services;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final Supplier<BlockEntityType<TestBlockEntity>> TEST = Services.REGISTRIES.registerBlockEntity(HeccoLib.MOD_ID, "test", TestBlockEntity::new, () -> ModBlocks.TEST_BLOCK.get());

    public static void register() {

    }
}
