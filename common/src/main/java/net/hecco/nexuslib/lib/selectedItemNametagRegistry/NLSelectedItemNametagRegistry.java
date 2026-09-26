package net.hecco.nexuslib.lib.selectedItemNametagRegistry;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class NLSelectedItemNametagRegistry {
    private static final Map<Item, BiFunction<ItemStack, Component, List<Component>>> REGISTRY = new LinkedHashMap<>();

    public static void register(ItemLike item, BiFunction<ItemStack, Component, List<Component>> components) {
        REGISTRY.put(item.asItem(), components);
    }

    public static Map<Item, BiFunction<ItemStack, Component, List<Component>>> getValues() {
        return REGISTRY;
    }
}
