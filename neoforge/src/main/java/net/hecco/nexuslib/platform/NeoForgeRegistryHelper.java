package net.hecco.nexuslib.platform;

import net.hecco.nexuslib.platform.services.NLRegistryHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import oshi.util.tuples.Pair;

import java.nio.file.Path;
import java.util.*;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;


public class NeoForgeRegistryHelper implements NLRegistryHelper {
    private final Map<ResourceKey<? extends Registry<?>>, DeferredRegister<?>> registries = new HashMap<>();
    private IEventBus eventBus = ModLoadingContext.get().getActiveContainer().getEventBus();

    public NeoForgeRegistryHelper(IEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public NeoForgeRegistryHelper() {
    }

    @Override
    public <T> Supplier<T> register(String modId, String id, Registry<T> registry1, Supplier<T> supplier) {
        DeferredRegister<T> deferredRegister = DeferredRegister.create(registry1.key(), modId);
        deferredRegister.register(eventBus);
        deferredRegister.register(id, supplier);
        return () -> registry1.get(ResourceLocation.fromNamespaceAndPath(modId, id));
    }

    @Override
    public <T> Holder<T> registerForHolder(String modId, String id, Registry<T> registry1, Supplier<T> holder) {
        DeferredRegister<T> registry;
        if (!registries.containsKey(registry1.key())) {
            var i = DeferredRegister.create((ResourceKey) registry1.key(), modId);
            i.register(eventBus);
            registries.put(registry1.key(), i);
        }
        registry = (DeferredRegister<T>) registries.get(registry1.key());
        var register = registry.register(id, holder);
        return register;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBlockEntityType(String modId, String id, Supplier<BlockEntityType<T>> supplier) {
        var registryKey = Registries.BLOCK_ENTITY_TYPE;
        if (!registries.containsKey(registryKey)) {
            var i = DeferredRegister.create((ResourceKey) registryKey, modId);
            i.register(eventBus);
            registries.put(registryKey, i);
        }
        var registry = (DeferredRegister<BlockEntityType<?>>) registries.get(registryKey);
        return registry.register(id, supplier);
    }

    @Override
    public <T extends BlockEntity> BlockEntityType<T> createBlockEntity(NLRegistryHelper.BlockEntitySupplier<T> supplier, Supplier<Block>... blocks) {
        return BlockEntityType.Builder.of(supplier::create, Arrays.stream(blocks).map(Supplier::get).toArray(Block[]::new)).build(null);
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public <T extends EntityType<?>> Supplier<T> registerEntityType(String modId, String id, Supplier<T> supplier) {
        DeferredRegister<T> registry;
        if (!registries.containsKey(Registries.ENTITY_TYPE)) {
            var i = DeferredRegister.create(Registries.ENTITY_TYPE, modId);
            i.register(eventBus);
            registries.put(Registries.ENTITY_TYPE, i);
        }
        registry = (DeferredRegister<T>) registries.get(Registries.ENTITY_TYPE);
        registry.register(id, supplier);
        return () -> (T) BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.fromNamespaceAndPath(modId, id));
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public Holder<SoundEvent> registerSoundReference(String modId, String id) {
        DeferredRegister<SoundEvent> registry;
        if (!registries.containsKey(Registries.SOUND_EVENT)) {
            var i = DeferredRegister.create((ResourceKey) Registries.SOUND_EVENT, modId);
            i.register(eventBus);
            registries.put(Registries.SOUND_EVENT, i);
        }
        registry = (DeferredRegister<SoundEvent>) registries.get(Registries.SOUND_EVENT);
        var register = registry.register(id, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(modId, id)));
        return register;
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public <T> Supplier<DataComponentType<T>> registerComponentType(String modId, String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        DeferredRegister.DataComponents registry;
        if (!registries.containsKey(Registries.DATA_COMPONENT_TYPE)) {
            registry = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, modId);
            registry.register(eventBus);
            registries.put(Registries.DATA_COMPONENT_TYPE, registry);
        } else {
            registry = (DeferredRegister.DataComponents) registries.get(Registries.DATA_COMPONENT_TYPE);
        }
        registry.registerComponentType(name, builder);
        return () -> (DataComponentType<T>) BuiltInRegistries.DATA_COMPONENT_TYPE.get(ResourceLocation.fromNamespaceAndPath(modId, name));
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public Supplier<SimpleParticleType> registerParticleType(String modid, String id) {
        DeferredRegister<SimpleParticleType> registry;
        if (!registries.containsKey(Registries.PARTICLE_TYPE)) {
            var i = DeferredRegister.create(Registries.PARTICLE_TYPE, modid);
            i.register(eventBus);
            registries.put(Registries.PARTICLE_TYPE, i);
        }
        registry = (DeferredRegister<SimpleParticleType>) registries.get(Registries.PARTICLE_TYPE);
        registry.register(id, () -> new SimpleParticleType(false));
        return () -> (SimpleParticleType) BuiltInRegistries.PARTICLE_TYPE.get(ResourceLocation.fromNamespaceAndPath(modid, id));
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public <T extends AbstractContainerMenu> Supplier<MenuType<T>> registerMenu(String modId, String id, MenuSupplier<T> factory) {
        DeferredRegister<MenuType<?>> registry;
        if (!registries.containsKey(Registries.MENU)) {
            var i = DeferredRegister.create(Registries.MENU, modId);
            i.register(eventBus);
            registries.put(Registries.MENU, i);
        }
        registry = (DeferredRegister<MenuType<?>>) registries.get(Registries.MENU);
        return registry.register(id, () -> new MenuType<>(factory::create, FeatureFlags.DEFAULT_FLAGS));
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public <T extends Recipe<?>> Supplier<RecipeType<T>> registerRecipeType(String modId, String id) {
        DeferredRegister<RecipeType<?>> registry;
        if (!registries.containsKey(Registries.RECIPE_TYPE)) {
            var i = DeferredRegister.create(Registries.RECIPE_TYPE, modId);
            i.register(eventBus);
            registries.put(Registries.RECIPE_TYPE, i);
        }
        registry = (DeferredRegister<RecipeType<?>>) registries.get(Registries.RECIPE_TYPE);

        return registry.register(id, () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(modId, id)));
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public <T extends Recipe<?>> Supplier<RecipeSerializer<T>> registerRecipeSerializer(String modId, String id, RecipeSerializer<T> serializer) {
        DeferredRegister<RecipeSerializer<?>> registry;
        if (!registries.containsKey(Registries.RECIPE_SERIALIZER)) {
            var i = DeferredRegister.create(Registries.RECIPE_SERIALIZER, modId);
            i.register(eventBus);
            registries.put(Registries.RECIPE_SERIALIZER, i);
        }
        registry = (DeferredRegister<RecipeSerializer<?>>) registries.get(Registries.RECIPE_SERIALIZER);

        return registry.register(id, () -> serializer);
    }


    @Override
    public void addItemsToItemGroup(ResourceKey<CreativeModeTab> tab, ArrayList<Pair<ItemLike, ItemStack>> items) {
    }

    @Override
    public void registerBuiltInResourcepack(String modId, String packId, String displayName, boolean required, boolean enabledByDefault) {
        this.eventBus.addListener((AddPackFindersEvent event) -> {
            if (event.getPackType() == PackType.CLIENT_RESOURCES) {
                Path path = ModList.get().getModFileById(modId).getFile().findResource("resourcepacks/" + packId);
                event.addRepositorySource(source -> source.accept(new Pack(
                        new PackLocationInfo(modId + ":" + packId, Component.literal(displayName), PackSource.BUILT_IN, Optional.empty()),
                        new PathPackResources.PathResourcesSupplier(path),
                        new Pack.Metadata(Component.empty(), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of(), false),
                        new PackSelectionConfig(required, Pack.Position.TOP, false)
                )));
            }
        });
    }

    @Override
    public void registerBuiltInDatapack(String modId, String packId, String displayName, boolean required, boolean enabledByDefault) {
        this.eventBus.addListener((AddPackFindersEvent event) -> {
            if (event.getPackType() == PackType.SERVER_DATA) {
                Path path = ModList.get().getModFileById(modId).getFile().findResource("resourcepacks/" + packId);
                event.addRepositorySource(source -> source.accept(new Pack(
                        new PackLocationInfo(modId + ":" + packId, Component.literal(displayName), PackSource.BUILT_IN, Optional.empty()),
                        new PathPackResources.PathResourcesSupplier(path),
                        new Pack.Metadata(Component.empty(), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of(), false),
                        new PackSelectionConfig(required, Pack.Position.TOP, false)
                )));
            }
        });
    }
}
