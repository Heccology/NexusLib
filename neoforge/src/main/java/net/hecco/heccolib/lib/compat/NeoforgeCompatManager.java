package net.hecco.heccolib.lib.compat;

import net.hecco.heccolib.HeccoLib;

public class NeoforgeCompatManager extends CompatManager{

    public NeoforgeCompatManager(String modId) {
        super(modId);
    }

    public NeoforgeCompatManager() {
        super(HeccoLib.MOD_ID);
    }

    @Override
    public void registerCompatContent() {
        for (CompatModule module : MODULES) {
            module.registerContent();
        }
    }
}
