package net.hecco.heccolib;


import net.hecco.heccolib.platform.NeoForgeClientHelper;
import net.hecco.heccolib.platform.NeoForgeRegistryHelper;
import net.hecco.heccolib.platform.HLServices;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(HeccoLib.MOD_ID)
public class HeccoLibForge {

    public HeccoLibForge() {
        IEventBus modEventBus = ModLoadingContext.get().getActiveContainer().getEventBus();

        HLServices.REGISTRY = new NeoForgeRegistryHelper(modEventBus);
//        HLServices.CLIENT = new NeoForgeClientHelper(modEventBus);

        if (modEventBus != null) {
            modEventBus.addListener(this::onCommonSetup);
        }
    }

    private void onCommonSetup(final FMLCommonSetupEvent event) {
        HeccoLib.init();
    }
}