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
//    @Override
//    public <T extends Block> Supplier<T> registerBlockNoItem(String modid, String id, Supplier<T> supplier) {
//        T block = Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(modid, id), supplier.get());
//        HeccoLib.LOGGER.info(block.toString());
//        return () -> block;
//    }
//
//    @Override
//    public <T extends Block> Supplier<T> registerBlock(String modid, String id, Supplier<T> supplier) {
//        T block = Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(modid, id), supplier.get());
//        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(modid, id), new BlockItem(block, new Item.Properties()));
//        return () -> block;
//    }
//
//    @Override
//    public <T extends Block> Supplier<T> registerBlock(String modid, String id, Supplier<T> block, Item.Properties itemProperties) {
//        T registeredBlock = Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(modid, id), block.get());
//        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(modid, id), new BlockItem(registeredBlock, itemProperties));
//        return () -> registeredBlock;
//    }
//
//    @Override
//    public <I extends Item> Supplier<I> registerItem(String modid, String id, Supplier<I> item) {
//        I registeredItem = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(modid, id), item.get());
//        return () -> registeredItem;
//    }

    @Override
    public <T> Supplier<T> register(String modid, String id, ResourceKey<? extends Registry<T>> registry, Supplier<T> entry) {
        Registry<? extends Registry<?>> registryOfRegistries = BuiltInRegistries.REGISTRY;
        @SuppressWarnings("unchecked")
        Registry<T> registry1 = (Registry<T>) registryOfRegistries.get(registry.location());
        T registeredEntry = Registry.register(registry1, ResourceLocation.fromNamespaceAndPath(modid, id), entry.get());
        return () -> registeredEntry;
    }
}
