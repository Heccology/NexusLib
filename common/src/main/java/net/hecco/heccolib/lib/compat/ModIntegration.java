package net.hecco.heccolib.lib.compat;

import net.minecraft.world.flag.FeatureElement;

import java.util.List;
import java.util.function.Supplier;

public interface ModIntegration {

    CompatManager compatManager();

    List<String> modIds();

    void registerContent();

    default Supplier<?> registerContent(Supplier<?> content) {
        compatManager().CONTENT.put(content, this);
        return content;
    }
}
