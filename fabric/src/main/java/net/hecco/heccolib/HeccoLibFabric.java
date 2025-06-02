package net.hecco.heccolib;

import net.fabricmc.api.ModInitializer;

public class HeccoLibFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        HeccoLib.init();
    }
}
