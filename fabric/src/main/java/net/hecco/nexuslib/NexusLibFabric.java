package net.hecco.nexuslib;

import net.fabricmc.api.ModInitializer;

public class NexusLibFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        NexusLib.init();
    }
}
