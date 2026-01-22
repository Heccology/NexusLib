package net.hecco.nexuslib.lib.compat;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureElement;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.*;

public class NLCompatAPI {
    private static final Map<String, CompatManager> MANAGERS = new HashMap<>();


    public static CompatManager createCompatManager(String modId) {
        CompatManager manager = new CompatManager(modId);
        MANAGERS.put(modId, manager);
        return manager;
    }

    public static Map<String, CompatManager> getManagers() {
        return MANAGERS;
    }

    public static final Set<ResourceLocation> DISABLED_CACHE = new ObjectOpenHashSet<>();

    public static final Map<FeatureElement, ResourceLocation> ID_CACHE = Collections.synchronizedMap(new WeakHashMap<>());
    public static ResourceLocation getId(FeatureElement element) {
        if (ID_CACHE.containsKey(element)) {
            return ID_CACHE.get(element);
        } else {
            ResourceLocation id = resolveId(element);
            ID_CACHE.put(element, id);
            return id;
        }
    }

    public static ResourceLocation resolveId(FeatureElement element) {
        if (element instanceof Item item)
            return BuiltInRegistries.ITEM.getKey(item);
        if (element instanceof Block block)
            return BuiltInRegistries.BLOCK.getKey(block);
        return null;
    }

}
