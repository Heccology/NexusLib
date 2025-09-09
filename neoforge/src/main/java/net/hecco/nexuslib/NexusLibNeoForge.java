package net.hecco.nexuslib;


import net.hecco.nexuslib.platform.NeoForgeRegistryHelper;
import net.hecco.nexuslib.platform.NLServices;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(NexusLib.MOD_ID)
public class NexusLibNeoForge {

    public NexusLibNeoForge() {
        IEventBus modEventBus = ModLoadingContext.get().getActiveContainer().getEventBus();

        NLServices.REGISTRY = new NeoForgeRegistryHelper(modEventBus);
//        HLServices.CLIENT = new NeoForgeClientHelper(modEventBus);

        if (modEventBus != null) {
            modEventBus.addListener(this::onCommonSetup);
        }
    }

    private void onCommonSetup(final FMLCommonSetupEvent event) {
        NexusLib.init();
    }
}