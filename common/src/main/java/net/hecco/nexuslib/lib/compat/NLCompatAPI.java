package net.hecco.nexuslib.lib.compat;

import net.hecco.nexuslib.platform.NLServices;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureElement;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

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

    public static boolean check(FeatureElement element) {
        for (CompatManager manager : MANAGERS.values()) {
            for (Map.Entry<Supplier<?>, ModIntegration> entry : manager.CONTENT.entrySet()) {
                if (entry.getKey().get() == element) {
                    ModIntegration integration = entry.getValue();
                    return !integration.modIds().stream().allMatch(NLServices.PLATFORM::isModLoaded) && !NLServices.PLATFORM.isDatagen();
                }
            }
        }
        return false;

    }

    @Nullable
    public static ResourceLocation getId(FeatureElement element) {
        if (element instanceof Item item)
            return BuiltInRegistries.ITEM.getKey(item);
        if (element instanceof Block block)
            return BuiltInRegistries.BLOCK.getKey(block);
        if (element instanceof EntityType<?> type)
            return BuiltInRegistries.ENTITY_TYPE.getKey(type);
        if (element instanceof MenuType<?> menu)
            return BuiltInRegistries.MENU.getKey(menu);
        if (element instanceof Potion potion)
            return BuiltInRegistries.POTION.getKey(potion);
        if (element instanceof MobEffect effect)
            return BuiltInRegistries.MOB_EFFECT.getKey(effect);
        return null;
    }

}
