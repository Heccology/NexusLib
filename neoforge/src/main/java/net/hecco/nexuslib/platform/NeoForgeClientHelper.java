package net.hecco.nexuslib.platform;

import net.hecco.nexuslib.platform.services.NLClientHelper;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

public class NeoForgeClientHelper implements NLClientHelper {

    private final IEventBus eventBus;

    public NeoForgeClientHelper(IEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public NeoForgeClientHelper() {
        this.eventBus = ModLoadingContext.get().getActiveContainer().getEventBus();
    }

    @Override
    public void setBlockRenderType(Block block, RenderType renderType) {
        ItemBlockRenderTypes.setRenderLayer(block, renderType);
    }

    @Override
    public void registerItemModelPredicate(Item item, ResourceLocation name, ClampedItemPropertyFunction property) {
        ItemProperties.register(item, name, property);
    }

    @Override
    public <T extends ParticleOptions> void registerParticle(ParticleType<T> type, ParticleProvider<T> registration) {
        eventBus.addListener((RegisterParticleProvidersEvent event) -> event.registerSpecial(type, registration));
    }
}
