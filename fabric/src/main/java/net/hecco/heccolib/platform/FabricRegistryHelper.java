package net.hecco.heccolib.platform;

import net.hecco.heccolib.HeccoLib;
import net.hecco.heccolib.platform.services.HLRegistryHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class FabricRegistryHelper implements HLRegistryHelper {

    @Override
    @SuppressWarnings("unchecked")
    public <T> Supplier<T> register(String modid, String id, ResourceKey<? extends Registry<T>> registry, Supplier<T> entry) {
        Registry<T> registry1 = (Registry<T>) BuiltInRegistries.REGISTRY.get(registry.location());
        T registeredEntry = Registry.register(registry1, ResourceLocation.fromNamespaceAndPath(modid, id), entry.get());
        return () -> registeredEntry;
    }
}
