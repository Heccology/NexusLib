package net.hecco.nexuslib;

import net.hecco.nexuslib.lib.postProcessShaderRegistry.NLPostProcessShaderRegistry;
import net.hecco.nexuslib.lib.untintedParticleRegistry.NLUntintedParticleRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NexusLib {

    public static final String MOD_ID = "nexuslib";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        NLUntintedParticleRegistry.add(Blocks.POTTED_FERN); //Fixed your game mojang hehahehaw
        NLPostProcessShaderRegistry.add((entity) -> entity instanceof Player, ResourceLocation.withDefaultNamespace("shaders/post/creeper.json"), false);
    }
}