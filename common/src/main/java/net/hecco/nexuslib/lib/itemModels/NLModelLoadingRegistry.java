package net.hecco.nexuslib.lib.itemModels;

import net.minecraft.client.resources.model.ModelResourceLocation;

import java.util.ArrayList;

public class NLModelLoadingRegistry {
    private static final ArrayList<ModelResourceLocation> ENTRIES = new ArrayList<>();

    public static void add(ModelResourceLocation location) {
        if (!ENTRIES.contains(location)) ENTRIES.add(location);
    }

    public static ArrayList<ModelResourceLocation> getEntries() {
        return ENTRIES;
    }

}
