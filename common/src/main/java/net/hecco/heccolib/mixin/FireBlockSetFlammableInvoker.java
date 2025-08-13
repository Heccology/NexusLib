package net.hecco.heccolib.mixin;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FireBlock.class)
public interface FireBlockSetFlammableInvoker {
    @Invoker("setFlammable")
    void heccolib$setFlammable(Block block, int encouragement, int flammability);
}
