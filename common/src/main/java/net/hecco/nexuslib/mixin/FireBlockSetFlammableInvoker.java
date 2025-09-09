package net.hecco.nexuslib.mixin;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FireBlock.class)
public interface FireBlockSetFlammableInvoker {
    @Invoker("setFlammable")
    void nexuslib$setFlammable(Block block, int encouragement, int flammability);
}
