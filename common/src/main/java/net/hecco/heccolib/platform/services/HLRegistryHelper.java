package net.hecco.heccolib.platform.services;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public interface HLRegistryHelper {

    default <T extends Block> Supplier<T> registerBlock(String modid, String id, Supplier<T> block) {
        return register(modid, id, (ResourceKey<? extends Registry<T>>) BuiltInRegistries.BLOCK.key(), block);
    }

//    <T extends Block> Supplier<T> registerBlock(String modid, String id, Supplier<T> block);
//
//    <T extends Block> Supplier<T> registerBlock(String modid, String id, Supplier<T> block, Item.Properties itemProperties);
//
//    <I extends Item> Supplier<I> registerItem(String modid, String id, Supplier<I> item);

    <T> Supplier<T> register(String modid, String id, ResourceKey<? extends Registry<T>> registry, Supplier<T> entry);
}
