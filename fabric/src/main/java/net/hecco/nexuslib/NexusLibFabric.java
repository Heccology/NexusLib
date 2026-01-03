package net.hecco.nexuslib;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.hecco.nexuslib.lib.loader_agnostic.commandRegistry.NLCommandRegistry;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class NexusLibFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        NexusLib.init();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            for (Consumer<CommandDispatcher<CommandSourceStack>> consumer : NLCommandRegistry.getValues()) {
                consumer.accept(dispatcher);
            }
            for (BiConsumer<CommandDispatcher<CommandSourceStack>, CommandBuildContext> consumer : NLCommandRegistry.getValues2()) {
                consumer.accept(dispatcher, registryAccess);
            }
        });
    }
}
