package net.hecco.heccolib.lib.compat;

import java.util.ArrayList;
import java.util.List;

public abstract class CompatManager {

    protected final String modId;
    protected final List<CompatModule> MODULES = new ArrayList<>();

    public CompatManager(String modId) {
        this.modId = modId;
    }

    public void addModule(CompatModule module) {
        MODULES.add(module);
    }

    public abstract void registerCompatContent();
}
