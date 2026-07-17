package net.hecco.nexuslib;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.hecco.nexuslib.lib.cape.CapePayloadHandler;
import net.hecco.nexuslib.lib.cape.SetCapePacket;
import net.hecco.nexuslib.lib.cape.SyncCapePacket;
import net.hecco.nexuslib.lib.loader_agnostic.commandRegistry.NLCommandRegistry;
import net.hecco.nexuslib.lib.util.NLCapeManager;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class NexusLibFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        NexusLib.init();

        CapePayloadHandler.register();
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            ClientPlayNetworking.registerGlobalReceiver(SyncCapePacket.TYPE, SyncCapePacket::handle);
        }
        ServerPlayNetworking.registerGlobalReceiver(SetCapePacket.TYPE, SetCapePacket::handle);
        ServerPlayConnectionEvents.JOIN.register(this::joinServerEvent);

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            for (Consumer<CommandDispatcher<CommandSourceStack>> consumer : NLCommandRegistry.getValues()) {
                consumer.accept(dispatcher);
            }
            for (BiConsumer<CommandDispatcher<CommandSourceStack>, CommandBuildContext> consumer : NLCommandRegistry.getValues2()) {
                consumer.accept(dispatcher, registryAccess);
            }
        });
    }

    public void joinServerEvent(ServerGamePacketListenerImpl handler, PacketSender sender, MinecraftServer server) {
        ServerPlayer player = handler.player;

        for (ServerPlayer other : player.serverLevel().players()) {
            ServerPlayNetworking.send(player, new SyncCapePacket(other.getUUID(), NLCapeManager.getSelected(other.getUUID())));

            if (other != player) {
                ServerPlayNetworking.send(other, new SyncCapePacket(player.getUUID(), NLCapeManager.getSelected(player.getUUID())));
            }
        }
    }
}
