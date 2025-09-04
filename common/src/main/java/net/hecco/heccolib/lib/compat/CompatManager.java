package net.hecco.heccolib.lib.compat;

import net.minecraft.world.flag.FeatureElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class CompatManager {

    protected final String modId;
    protected final List<ModIntegration> INTEGRATIONS = new ArrayList<>();
    protected final Map<Supplier<?>, ModIntegration> CONTENT = new HashMap<>();

    protected CompatManager(String modId) {
        this.modId = modId;
    }

    public void addIntegration(ModIntegration module) {
        INTEGRATIONS.add(module);
    }

    public void registerCompatContent() {
        for (ModIntegration integration : INTEGRATIONS) {
            integration.registerContent();
        }
    }
}
