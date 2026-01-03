package net.hecco.nexuslib.lib.loader_agnostic.commandRegistry;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.*;

public class NLCommandRegistry {
    private static final List<Consumer<CommandDispatcher<CommandSourceStack>>> COMMANDS = new ArrayList<>();
    private static final List<BiConsumer<CommandDispatcher<CommandSourceStack>, CommandBuildContext>> COMMANDS2 = new ArrayList<>();

    public static void add(BiConsumer<CommandDispatcher<CommandSourceStack>, CommandBuildContext> consumer) {
        COMMANDS2.add(consumer);
    }
    public static void add(Consumer<CommandDispatcher<CommandSourceStack>> consumer) {
        COMMANDS.add(consumer);
    }

    public static List<Consumer<CommandDispatcher<CommandSourceStack>>> getValues() {
        return COMMANDS;
    }
    public static List<BiConsumer<CommandDispatcher<CommandSourceStack>, CommandBuildContext>> getValues2() {
        return COMMANDS2;
    }
}
