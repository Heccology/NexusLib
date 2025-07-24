package net.hecco.heccolib.platform;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.hecco.heccolib.HeccoLib;
import net.hecco.heccolib.platform.services.HLRegistryHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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

    @Override
    @SuppressWarnings("unchecked")
    public <T> Holder<T> registerHolder(String modId, String id, ResourceKey<? extends Registry<T>> registry, T entry) {
        return Registry.registerForHolder(
                (Registry<T>) BuiltInRegistries.REGISTRY.get(registry.location()),
                ResourceLocation.fromNamespaceAndPath(modId, id),
                entry
        );
    }

    @Override
    public <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBlockEntityType(String modid, String id, Supplier<BlockEntityType<T>> supplier) {
        BlockEntityType<T> register = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(modid, id), supplier.get());
        return () -> register;
    }

    @Override
    public <T extends BlockEntity> BlockEntityType<T> createBlockEntity(HLRegistryHelper.BlockEntitySupplier<T> supplier, Supplier<Block>... blocks) {
        return FabricBlockEntityTypeBuilder.create(supplier::create, Arrays.stream(blocks).map(Supplier::get).toArray(Block[]::new)).build();
    }

    @Override
    public <T extends EntityType<?>> Supplier<T> registerEntityType(String modid, String id, Supplier<T> supplier) {
        return () -> Registry.register(BuiltInRegistries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(modid, id), supplier.get());
    }

    @Override
    public Supplier<SimpleParticleType> registerParticleType(String modid, String id) {
        return () -> Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(modid, id), FabricParticleTypes.simple());
    }

    @Override
    public void addItemsToItemGroup(ResourceKey<CreativeModeTab> tab, ArrayList<Pair<ItemLike, ItemLike>> items) {
        ItemGroupEvents.modifyEntriesEvent(tab).register(entries -> {
                    for (Pair<ItemLike, ItemLike> entry : items) {
                        entries.addAfter(entry.getA(), entry.getB());
                    }
                });
    }


}