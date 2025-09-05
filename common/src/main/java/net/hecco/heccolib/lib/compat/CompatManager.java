package net.hecco.heccolib.lib.compat;

import net.hecco.heccolib.HeccoLib;
import net.hecco.heccolib.platform.HLServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class CompatManager {

    protected final String modId;
    protected final List<ModIntegration> INTEGRATIONS = new ArrayList<>();
    public final Map<Supplier<?>, ModIntegration> CONTENT = new HashMap<>();

    protected CompatManager(String modId) {
        this.modId = modId;
    }

    public void addIntegration(ModIntegration module) {
        INTEGRATIONS.add(module);
    }

    public List<ModIntegration> getIntegrations() {
        return this.INTEGRATIONS;
    }

    public void registerCompatContent() {
        for (ModIntegration integration : INTEGRATIONS) {
            integration.registerContent();
            if (integration.shouldCreateDatapack()) {
                if (integration.modIds().isEmpty()) {
                    HeccoLib.LOGGER.error("Cannot create datapack with no mod ids for integration " + integration);
                    continue;
                }
                StringBuilder id = new StringBuilder();
                for (String modId : integration.modIds()) {
                    id.append(modId).append("_");
                }
                id.append("dat");
                HLServices.REGISTRY.registerBuiltInDatapack(modId, id.toString(), integration.getDatapackName() != null ? integration.getDatapackName() : id.toString());
            }
        }
    }
}
