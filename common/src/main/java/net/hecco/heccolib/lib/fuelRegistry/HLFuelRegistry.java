package net.hecco.heccolib.lib.fuelRegistry;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.LinkedHashMap;
import java.util.Map;

public class HLFuelRegistry {
    private static final Map<Item, Integer> FUELS = new LinkedHashMap<>();

    public static void add(ItemLike item, int burnTime) {
        FUELS.put(item.asItem(), burnTime);
    }

    public static Map<Item, Integer> getFuels() {
        return FUELS;
    }
}
