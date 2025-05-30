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

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> Supplier<T> register(String modid, String id, ResourceKey<? extends Registry<T>> registryKey, Supplier<T> entry) {
        DeferredRegister<T> registry;
        if (!genericRegistries.containsKey(registryKey)) {
            var i = DeferredRegister.create((ResourceKey) registryKey, modid);
            i.register(eventBus);
            genericRegistries.put(registryKey, i);
        }
        registry = (DeferredRegister<T>) genericRegistries.get(registryKey);
        return registry.register(id, entry);
    }
}
