package net.hecco.heccolib.platform;

import net.hecco.heccolib.platform.services.HLClientHelper;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;

public class NeoForgeClientHelper implements HLClientHelper {
    @Override
    public void setBlockRenderType(Block block, RenderType renderType) {
        ItemBlockRenderTypes.setRenderLayer(block, renderType);
    }
}
