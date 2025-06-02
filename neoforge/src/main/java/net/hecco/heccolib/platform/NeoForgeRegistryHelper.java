package net.hecco.heccolib.platform;

import net.hecco.heccolib.platform.services.HLRegistryHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;


public class NeoForgeRegistryHelper implements HLRegistryHelper {
    private final Map<ResourceKey<? extends Registry<?>>, DeferredRegister<?>> registries = new HashMap<>();
    private final IEventBus eventBus;

    public NeoForgeRegistryHelper(IEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public NeoForgeRegistryHelper() {
        this.eventBus = ModLoadingContext.get().getActiveContainer().getEventBus();
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
    public <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBlockEntity(String modId, String id, HLRegistryHelper.BlockEntitySupplier<T> supplier, Supplier<Block>... blockSuppliers) {
        var registryKey = Registries.BLOCK_ENTITY_TYPE;
        if (!registries.containsKey(registryKey)) {
            var i = DeferredRegister.create((ResourceKey) registryKey, modId);
            i.register(eventBus);
            registries.put(registryKey, i);
        }
        var registry = (DeferredRegister<BlockEntityType<?>>) registries.get(registryKey);
        return registry.register(id, () -> {
            Block[] blocks = Arrays.stream(blockSuppliers).map(Supplier::get).toArray(Block[]::new);
            return BlockEntityType.Builder.of(supplier::create, blocks).build(null);
        });
    }
}
