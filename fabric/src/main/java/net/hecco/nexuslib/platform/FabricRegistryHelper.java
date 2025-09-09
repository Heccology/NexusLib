package net.hecco.nexuslib.platform;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.hecco.nexuslib.platform.services.NLRegistryHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import oshi.util.tuples.Pair;

import java.util.*;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class FabricRegistryHelper implements NLRegistryHelper {

    @Override
    public <T> Supplier<T> register(String modid, String id, Registry<T> registry, Supplier<T> supplier) {
        T value = Registry.register(
                registry,
                ResourceLocation.fromNamespaceAndPath(modid, id),
                supplier.get()
        );
        return () -> value;
    }

    @Override
    public <T> Holder<T> registerForHolder(String modid, String id, Registry<T> registry, Supplier<T> holder) {
        var value = Registry.registerForHolder(
                registry,
                ResourceLocation.fromNamespaceAndPath(modid, id),
                holder.get()
        );
        return value;
    }

    @Override
    public <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBlockEntityType(String modid, String id, Supplier<BlockEntityType<T>> supplier) {
        BlockEntityType<T> register = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(modid, id), supplier.get());
        return () -> register;
    }

    @Override
    public <T extends BlockEntity> BlockEntityType<T> createBlockEntity(NLRegistryHelper.BlockEntitySupplier<T> supplier, Supplier<Block>... blocks) {
        var register = FabricBlockEntityTypeBuilder.create(supplier::create, Arrays.stream(blocks).map(Supplier::get).toArray(Block[]::new)).build();
        return register;
    }

    @Override
    public <T extends EntityType<?>> Supplier<T> registerEntityType(String modid, String id, Supplier<T> supplier) {
        var registered = Registry.register(BuiltInRegistries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(modid, id), supplier.get());
        return () -> registered;
    }

    @Override
    public Holder<SoundEvent> registerSoundReference(String modId, String id) {
        return Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, ResourceLocation.fromNamespaceAndPath(modId, id), SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(modId, id)));
    }

    @Override
    public <T> Supplier<DataComponentType<T>> registerComponentType(String modId, String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        DataComponentType<T> instance = builder.apply(DataComponentType.builder()).build();
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ResourceLocation.fromNamespaceAndPath(modId, name), instance);
        return () -> instance;
    }

    @Override
    public Supplier<SimpleParticleType> registerParticleType(String modid, String id) {
        var register = Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(modid, id), FabricParticleTypes.simple());;
        return () -> register;
    }

    @Override
    public <T extends AbstractContainerMenu> Supplier<MenuType<T>> registerMenu(String modId, String id, MenuSupplier<T> factory) {
        MenuType<T> registered = Registry.register(BuiltInRegistries.MENU, ResourceLocation.fromNamespaceAndPath(modId, id), new MenuType<>(factory::create, FeatureFlags.DEFAULT_FLAGS));;
        return () -> registered;
    }

    @Override
    public <T extends Recipe<?>> Supplier<RecipeType<T>> registerRecipeType(String modId, String id) {
        var registered = Registry.register(BuiltInRegistries.RECIPE_TYPE, ResourceLocation.fromNamespaceAndPath(modId, id), new RecipeType<T>() {});
        return () -> registered;
    }

    @Override
    public <T extends Recipe<?>> Supplier<RecipeSerializer<T>> registerRecipeSerializer(String modId, String id, RecipeSerializer<T> serializer) {
        var registered = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, ResourceLocation.fromNamespaceAndPath(modId, id), serializer);
        return () -> registered;
    }

    @Override
    public void addItemsToItemGroup(ResourceKey<CreativeModeTab> tab, ArrayList<Pair<ItemLike, ItemStack>> items) {
        ItemGroupEvents.modifyEntriesEvent(tab).register(entries -> {
                    for (Pair<ItemLike, ItemStack> entry : items) {
                        entries.addAfter(entry.getA(), entry.getB());
                    }
                });
    }

    @Override
    public void registerBuiltInResourcepack(String modId, String packId, String displayName, boolean required, boolean enabledByDefault) {
        Optional<ModContainer> container = FabricLoader.getInstance().getModContainer(modId);
        if (container.isPresent()) {
            ResourceManagerHelper.registerBuiltinResourcePack(
                    ResourceLocation.fromNamespaceAndPath(modId, packId),
                    container.get(),
                    Component.literal(displayName),
                    required ? ResourcePackActivationType.ALWAYS_ENABLED : enabledByDefault ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
            );
        }
    }

    @Override
    public void registerBuiltInDatapack(String modId, String packId, String displayName, boolean required, boolean enabledByDefault) {
        Optional<ModContainer> container = FabricLoader.getInstance().getModContainer(modId);
        if (container.isPresent()) {
            ResourceManagerHelper.registerBuiltinResourcePack(
                    ResourceLocation.fromNamespaceAndPath(modId, packId),
                    container.get(),
                    Component.literal(displayName),
                    required ? ResourcePackActivationType.ALWAYS_ENABLED : enabledByDefault ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
            );
        }
    }


}