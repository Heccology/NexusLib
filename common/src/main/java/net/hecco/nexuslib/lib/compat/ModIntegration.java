package net.hecco.nexuslib.lib.compat;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public interface ModIntegration {

    CompatManager getCompatManager();

    List<String> modIds();

    void registerContent();

    default Supplier<?> registerContent(Supplier<?> content) {
        getCompatManager().CONTENT.put(content, this);
        return content;
    }

    default boolean shouldCreateDatapack() {return false;}

    @Nullable
    default String getDatapackName() {return null;}

    default void recipeGeneration(RecipeOutput output) {}

    default Criterion<InventoryChangeTrigger.TriggerInstance> has(ResourceLocation id) {
        return CriteriaTriggers.INVENTORY_CHANGED.createCriterion(new InventoryChangeTrigger.TriggerInstance(Optional.empty(), InventoryChangeTrigger.TriggerInstance.Slots.ANY, List.of(ItemPredicate.Builder.item().of(BuiltInRegistries.ITEM.get(id)).build())));
    }
    default Criterion<InventoryChangeTrigger.TriggerInstance> has(TagKey<Item> tag) {
        return CriteriaTriggers.INVENTORY_CHANGED.createCriterion(new InventoryChangeTrigger.TriggerInstance(Optional.empty(), InventoryChangeTrigger.TriggerInstance.Slots.ANY, List.of(ItemPredicate.Builder.item().of(tag).build())));
    }
}
