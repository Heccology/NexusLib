package net.hecco.nexuslib;


import com.mojang.brigadier.CommandDispatcher;
import net.hecco.nexuslib.lib.loader_agnostic.commandRegistry.NLCommandRegistry;
import net.hecco.nexuslib.platform.NeoForgeRegistryHelper;
import net.hecco.nexuslib.platform.NLServices;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Mod(NexusLib.MOD_ID)
public class NexusLibNeoForge {

    public NexusLibNeoForge() {
        IEventBus modEventBus = ModLoadingContext.get().getActiveContainer().getEventBus();

        NLServices.REGISTRY = new NeoForgeRegistryHelper(modEventBus);
//        HLServices.CLIENT = new NeoForgeClientHelper(modEventBus);

        if (modEventBus != null) {
            modEventBus.addListener(this::onCommonSetup);
        }
        NeoForge.EVENT_BUS.addListener(this::registerCommands);
    }

    @SubscribeEvent
    public void registerCommands(RegisterCommandsEvent event) {
        for (Consumer<CommandDispatcher<CommandSourceStack>> consumer : NLCommandRegistry.getValues()) {
            consumer.accept(event.getDispatcher());
        }
        for (BiConsumer<CommandDispatcher<CommandSourceStack>, CommandBuildContext> consumer : NLCommandRegistry.getValues2()) {
            consumer.accept(event.getDispatcher(), event.getBuildContext());
        }
    }
    private void onCommonSetup(final FMLCommonSetupEvent event) {
        NexusLib.init();
    }
}