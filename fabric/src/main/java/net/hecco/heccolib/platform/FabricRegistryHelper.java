package net.hecco.heccolib.platform;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.hecco.heccolib.HeccoLib;
import net.hecco.heccolib.platform.services.HLRegistryHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class FabricRegistryHelper implements HLRegistryHelper {

    @Override
    @SuppressWarnings("unchecked")
    public <T> Supplier<T> register(String modid, String id, ResourceKey<? extends Registry<T>> registryKey, Supplier<T> entry) {
        T value = Registry.register(
                (Registry<T>) BuiltInRegistries.REGISTRY.get(registryKey.location()),
                ResourceLocation.fromNamespaceAndPath(modid, id),
                entry.get()
        );
        return () -> value;
    }

    @SafeVarargs
    @Override
    public final <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBlockEntity(String modid, String id, HLRegistryHelper.BlockEntitySupplier<T> supplier, Supplier<Block>... blocks) {
        var blockEntity = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(modid, id), FabricBlockEntityTypeBuilder.create(supplier::create, Arrays.stream(blocks).map(Supplier::get).toArray(Block[]::new)).build());
        return () -> blockEntity;
    }
}