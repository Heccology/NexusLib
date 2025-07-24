package net.hecco.heccolib.platform;

import net.hecco.heccolib.platform.services.HLRegistryHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;


public class NeoForgeRegistryHelper implements HLRegistryHelper {
    private final Map<ResourceKey<? extends Registry<?>>, DeferredRegister<?>> registries = new HashMap<>();
    private final IEventBus eventBus;

    public NeoForgeRegistryHelper(IEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public NeoForgeRegistryHelper() {
        this.eventBus = null;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> Supplier<T> register(String modid, String id, ResourceKey<? extends Registry<T>> registryKey, Supplier<T> entry) {
        DeferredRegister<T> registry;
        if (!registries.containsKey(registryKey)) {
            var i = DeferredRegister.create((ResourceKey) registryKey, modid);
            i.register(eventBus);
            registries.put(registryKey, i);
        }
        registry = (DeferredRegister<T>) registries.get(registryKey);
        return registry.register(id, entry);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> Holder<T> registerHolder(String modId, String id, ResourceKey<? extends Registry<T>> registryKey, T entry) {
        DeferredRegister<T> registry;
        if (!registries.containsKey(registryKey)) {
            var i = DeferredRegister.create((ResourceKey) registryKey, modId);
            i.register(eventBus);
            registries.put(registryKey, i);
        }
        registry = (DeferredRegister<T>) registries.get(registryKey);
        return registry.register(id, () -> entry);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBlockEntityType(String modId, String id, Supplier<BlockEntityType<T>> supplier) {
        var registryKey = Registries.BLOCK_ENTITY_TYPE;
        if (!registries.containsKey(registryKey)) {
            var i = DeferredRegister.create((ResourceKey) registryKey, modId);
            i.register(eventBus);
            registries.put(registryKey, i);
        }
        var registry = (DeferredRegister<BlockEntityType<?>>) registries.get(registryKey);
        return registry.register(id, supplier);
    }

    @Override
    public <T extends BlockEntity> BlockEntityType<T> createBlockEntity(HLRegistryHelper.BlockEntitySupplier<T> supplier, Supplier<Block>... blocks) {
        return BlockEntityType.Builder.of(supplier::create, Arrays.stream(blocks).map(Supplier::get).toArray(Block[]::new)).build(null);
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public <T extends EntityType<?>> Supplier<T> registerEntityType(String modid, String id, Supplier<T> supplier) {
        DeferredRegister<T> registry;
        if (!registries.containsKey(Registries.ENTITY_TYPE)) {
            var i = DeferredRegister.create(Registries.ENTITY_TYPE, modid);
            i.register(eventBus);
            registries.put(Registries.ENTITY_TYPE, i);
        }
        registry = (DeferredRegister<T>) registries.get(Registries.ENTITY_TYPE);
        return registry.register(id, supplier);
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public Supplier<SimpleParticleType> registerParticleType(String modid, String id) {
        DeferredRegister<SimpleParticleType> registry;
        if (!registries.containsKey(Registries.PARTICLE_TYPE)) {
            var i = DeferredRegister.create(Registries.PARTICLE_TYPE, modid);
            i.register(eventBus);
            registries.put(Registries.PARTICLE_TYPE, i);
        }
        registry = (DeferredRegister<SimpleParticleType>) registries.get(Registries.PARTICLE_TYPE);
        return registry.register(id, () -> new SimpleParticleType(false));
    }

    @Override
    public void addItemsToItemGroup(ResourceKey<CreativeModeTab> tab, ArrayList<Pair<ItemLike, ItemLike>> items) {
    }
}
