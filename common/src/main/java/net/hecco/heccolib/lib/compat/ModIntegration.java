package net.hecco.heccolib.lib.compat;

import net.minecraft.data.recipes.RecipeOutput;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public abstract class ModIntegration {

    abstract CompatManager getCompatManager();

    abstract List<String> modIds();

    abstract void registerContent();

    public Supplier<?> registerContent(Supplier<?> content) {
        getCompatManager().CONTENT.put(content, this);
        return content;
    }

    public boolean shouldCreateDatapack() {return false;}

    @Nullable
    public String getDatapackName() {return null;}

    public void recipeGeneration(RecipeOutput output) {}
}
