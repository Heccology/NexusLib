package net.hecco.heccolib.platform.services;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Supplier;

public interface HLRegistryHelper {

    <T> Supplier<T> register(String modId, String id, ResourceKey<? extends Registry<T>> registry, Supplier<T> entry);

    <T> Holder<T> registerHolder(String modId, String id, ResourceKey<? extends Registry<T>> registry, T entry);

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

    <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBlockEntityType(String modid, String id, Supplier<BlockEntityType<T>> supplier);

    <T extends BlockEntity> BlockEntityType<T> createBlockEntity(BlockEntitySupplier<T> supplier, Supplier<Block>... blocks);

    @FunctionalInterface
    interface BlockEntitySupplier<T extends BlockEntity> {

        @NotNull T create(BlockPos pos, BlockState state);
    }

    <T extends EntityType<?>> Supplier<T> registerEntityType(String modid, String id, Supplier<T> supplier);

    Supplier<SimpleParticleType> registerParticleType(String modid, String id);

    void addItemsToItemGroup(ResourceKey<CreativeModeTab> tab, ArrayList<Pair<ItemLike, ItemLike>> items);
}
