package net.hecco.heccolib.platform.services;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public interface HLRegistryHelper {

    @SuppressWarnings("unchecked")
    default <T extends Block> Supplier<T> registerBlock(String modid, String id, Supplier<T> block) {
        Supplier<T> registeredBlock = register(modid, id, (ResourceKey<? extends Registry<T>>) BuiltInRegistries.BLOCK.key(), block);
        register(modid, id, BuiltInRegistries.ITEM.key(), () -> new BlockItem(registeredBlock.get(), new Item.Properties()));
        return registeredBlock;
    }

    @SuppressWarnings("unchecked")
    default <T extends Block> Supplier<T> registerBlockNoItem(String modid, String id, Supplier<T> block) {
        return register(modid, id, (ResourceKey<? extends Registry<T>>) BuiltInRegistries.BLOCK.key(), block);
    }

    @SuppressWarnings("unchecked")
    default <T extends Block> Supplier<T> registerBlock(String modid, String id, Supplier<T> block, Item.Properties itemProperties) {
        Supplier<T> registeredBlock = register(modid, id, (ResourceKey<? extends Registry<T>>) BuiltInRegistries.BLOCK.key(), block);
        register(modid, id, BuiltInRegistries.ITEM.key(), () -> new BlockItem(registeredBlock.get(), itemProperties));
        return registeredBlock;
    }

    @SuppressWarnings("unchecked")
    default <T extends Item> Supplier<T> registerItem(String modid, String id, Supplier<T> item) {
        return register(modid, id, (ResourceKey<? extends Registry<T>>) BuiltInRegistries.ITEM.key(), item);
    }

    <T> Supplier<T> register(String modid, String id, ResourceKey<? extends Registry<T>> registry, Supplier<T> entry);
}
