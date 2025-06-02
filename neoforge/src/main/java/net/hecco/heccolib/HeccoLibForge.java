package net.hecco.heccolib;


import net.hecco.heccolib.platform.NeoForgeRegistryHelper;
import net.hecco.heccolib.platform.HLServices;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(HeccoLib.MOD_ID)
public class HeccoLibForge {

    public HeccoLibForge(IEventBus eventBus) {
        HLServices.REGISTRY = new NeoForgeRegistryHelper(eventBus);
        HeccoLib.init();
    }
}