package net.hecco.nexuslib.lib.itemModels;

import com.mojang.datafixers.util.Function3;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;

public class NLItemModelOverrideRegistry {
    private static final ArrayList<Function3<ItemStack, ItemDisplayContext, Boolean, BakedModel>> ENTRIES = new ArrayList<>();
    //Stack, Display Context, Left Handed, return null if no override

    public static void add(Function3<ItemStack, ItemDisplayContext, Boolean, BakedModel> function) {
        ENTRIES.add(function);
    }

    public static ArrayList<Function3<ItemStack, ItemDisplayContext, Boolean, BakedModel>> getEntries() {
        return ENTRIES;
    }

}
