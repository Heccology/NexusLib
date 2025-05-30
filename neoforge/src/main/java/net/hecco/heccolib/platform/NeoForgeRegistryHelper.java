package net.hecco.heccolib.platform;

import net.hecco.heccolib.platform.services.HLRegistryHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;


public class NeoForgeRegistryHelper implements HLRegistryHelper {
    private final Map<ResourceKey<? extends Registry<?>>, DeferredRegister<?>> genericRegistries = new HashMap<>();
    private final IEventBus eventBus;

    public NeoForgeRegistryHelper(IEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public NeoForgeRegistryHelper() {
        this.eventBus = null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Supplier<T> register(String modid, String id, ResourceKey<? extends Registry<T>> registryKey, Supplier<T> entry) {
        DeferredRegister<T> deferred = (DeferredRegister<T>) genericRegistries.computeIfAbsent(registryKey, key -> {
            @SuppressWarnings("rawtypes")
            var i = DeferredRegister.create((ResourceKey) key, modid);
            i.register(eventBus);
            return i;
        });
        return deferred.register(id, entry);
    }
}
