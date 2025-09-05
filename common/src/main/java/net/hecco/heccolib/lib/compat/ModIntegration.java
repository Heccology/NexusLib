package net.hecco.heccolib.lib.compat;

import net.minecraft.data.recipes.RecipeOutput;
import org.jetbrains.annotations.Nullable;

import java.util.List;
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
}
