package net.hecco.nexuslib.platform.services;

import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public interface NLClientHelper {

    void setBlockRenderType(Block block, RenderType renderType);

    void registerItemModelPredicate(Item item, ResourceLocation name, ClampedItemPropertyFunction property);

    <T extends ParticleOptions> void registerParticle(ParticleType<T> type, ParticleProvider<T> registration);
}
