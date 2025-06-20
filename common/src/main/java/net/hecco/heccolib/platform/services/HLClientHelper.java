package net.hecco.heccolib.platform.services;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;

public interface HLClientHelper {
    void setBlockRenderType(Block block, RenderType renderType);
}
