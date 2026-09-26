package net.hecco.nexuslib;

import net.hecco.nexuslib.lib.selectedItemNametagRegistry.NLSelectedItemNametagRegistry;
import net.hecco.nexuslib.lib.untintedParticleRegistry.NLUntintedParticleRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class NexusLib {

    public static final String MOD_ID = "nexuslib";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        NLUntintedParticleRegistry.add(Blocks.POTTED_FERN); //Fixed your game mojang hehahehaw
    }

    public static ResourceLocation id(String id) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, id);
    }
}