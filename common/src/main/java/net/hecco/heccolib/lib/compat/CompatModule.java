package net.hecco.heccolib.lib.compat;

import com.jcraft.jorbis.Block;
import net.hecco.heccolib.lib.compat.compatBlocks.CompatBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public interface CompatModule {

    String modId();

    void registerContent();

    default Supplier<CompatBlock> compatBlock(BlockBehaviour.Properties blockBehaviour) {
        return () -> new CompatBlock(modId(), blockBehaviour);
    }
}
