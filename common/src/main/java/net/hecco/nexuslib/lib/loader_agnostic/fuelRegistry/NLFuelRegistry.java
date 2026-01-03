package net.hecco.nexuslib.lib.loader_agnostic.fuelRegistry;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.LinkedHashMap;
import java.util.Map;

public class NLFuelRegistry {
    private static final Map<Item, Integer> FUELS = new LinkedHashMap<>();

    public static void add(ItemLike item, int burnTime) {
        FUELS.put(item.asItem(), burnTime);
    }

    public static Map<Item, Integer> getFuels() {
        return FUELS;
    }
}
