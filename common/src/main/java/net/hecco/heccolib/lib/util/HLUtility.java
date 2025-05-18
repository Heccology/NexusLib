package net.hecco.heccolib.lib.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class HLUtility {
    public static Set<ResourceLocation> allBlockIdsInNamespace(String namespace) {
        Set<ResourceLocation> set = BuiltInRegistries.BLOCK.keySet();
        Set<ResourceLocation> a = new HashSet<>();
        for (ResourceLocation id : set) {
            if(Objects.equals(id.getNamespace(), namespace)) {
                a.add(id);
            }
        }
        return a;
    }
    public static Set<ResourceLocation> allItemIdsInNamespace(String namespace) {
        Set<ResourceLocation> set = BuiltInRegistries.ITEM.keySet();
        Set<ResourceLocation> a = new HashSet<>();
        for (ResourceLocation id : set) {
            if(Objects.equals(id.getNamespace(), namespace)) {
                a.add(id);
            }
        }
        return a;
    }

    public static String toSentanceCase(String s) {
        String[] words = s.split("[\\s|_]");
        StringBuilder capitalizeWord = new StringBuilder();
        for (String w : words){
            String first = w.substring(0,1);
            String afterFirst = w.substring(1);
            capitalizeWord
                    .append(first.toUpperCase())
                    .append(afterFirst)
                    .append(" ");
        }
        return capitalizeWord.toString().trim();
    }
}
