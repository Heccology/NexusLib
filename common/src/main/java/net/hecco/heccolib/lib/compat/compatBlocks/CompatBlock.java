package net.hecco.heccolib.lib.compat.compatBlocks;

import net.hecco.heccolib.platform.HLServices;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.block.Block;

public class CompatBlock extends Block {
    private final String modId;
    public CompatBlock(String modId, Properties properties) {
        super(properties);
        this.modId = modId;
    }

    @Override
    public boolean isEnabled(FeatureFlagSet enabledFeatures) {
        return HLServices.PLATFORM.isModLoaded(modId) || HLServices.PLATFORM.isDatagen();
    }
}
