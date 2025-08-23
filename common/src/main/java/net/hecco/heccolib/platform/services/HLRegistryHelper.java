package net.hecco.heccolib.platform.services;

import net.hecco.heccolib.mixin.FireBlockSetFlammableInvoker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public interface HLRegistryHelper {

    <T> Supplier<T> register(String modid, String id, Registry<T> registry, Supplier<T> supplier);

    <T> Holder<T> registerForHolder(String modid, String id, Registry<T> registry, Supplier<T> holder);

    default <T extends Block> Supplier<T> registerBlock(String modid, String id, Supplier<T> block) {
        Supplier<T> registeredBlock = registerBlockNoItem(modid, id, block);
        registerItem(modid, id, () -> new BlockItem(registeredBlock.get(), new Item.Properties()));
        return registeredBlock;
    }

    <T extends Block> Supplier<T> registerBlockNoItem(String modid, String id, Supplier<T> block);

    default <T extends Block> Supplier<T> registerBlock(String modid, String id, Supplier<T> block, Item.Properties itemProperties) {
        Supplier<T> registeredBlock = registerBlockNoItem(modid, id, block);
        registerItem(modid, id, () -> new BlockItem(registeredBlock.get(), itemProperties));
        return registeredBlock;
    }

    <T extends Item> Supplier<T> registerItem(String modid, String id, Supplier<T> item);

    <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBlockEntityType(String modid, String id, Supplier<BlockEntityType<T>> supplier);

    <T extends BlockEntity> BlockEntityType<T> createBlockEntity(BlockEntitySupplier<T> supplier, Supplier<Block>... blocks);

    @FunctionalInterface
    interface BlockEntitySupplier<T extends BlockEntity> {

        @NotNull T create(BlockPos pos, BlockState state);
    }

    <T extends EntityType<?>> Supplier<T> registerEntityType(String modid, String id, Supplier<T> supplier);

    <T> Supplier<DataComponentType<T>> registerComponentType(String modId, String name, UnaryOperator<DataComponentType.Builder<T>> builder);

    Supplier<SimpleParticleType> registerParticleType(String modid, String id);

    <T extends AbstractContainerMenu> Supplier<MenuType<T>> registerMenu(String modId, String id, MenuSupplier<T> factory);

    <T extends Recipe<?>> Supplier<RecipeType<T>> registerRecipeType(String modId, String id);

    <T extends Recipe<?>> Supplier<RecipeSerializer<T>> registerRecipeSerializer(String modId, String id, RecipeSerializer<T> serializer);

    @FunctionalInterface
    interface MenuSupplier<T extends AbstractContainerMenu> {

        @NotNull T create(int var1, Inventory var2);
    }

    default void setFlammable(Block block, int encouragement, int flammability) {
        ((FireBlockSetFlammableInvoker) Blocks.FIRE).heccolib$setFlammable(block, encouragement, flammability);
    }

    default void setFlammable(TagKey<Block> block, int encouragement, int flammability) {
        for(Holder<Block> holder : BuiltInRegistries.BLOCK.getTagOrEmpty(block)) {
            ((FireBlockSetFlammableInvoker) Blocks.FIRE).heccolib$setFlammable(holder.value(), encouragement, flammability);
        }
    }

    void addItemsToItemGroup(ResourceKey<CreativeModeTab> tab, ArrayList<Pair<ItemLike, ItemStack>> items);
}
