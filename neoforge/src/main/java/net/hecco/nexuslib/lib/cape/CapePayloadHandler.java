package net.hecco.nexuslib.lib.cape;

import net.hecco.nexuslib.NexusLib;
import net.hecco.nexuslib.lib.util.NLCapeManager;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public final class CapePayloadHandler {
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(NexusLib.MOD_ID).versioned("1");

        registrar.playToServer(SetCapePacket.TYPE, SetCapePacket.STREAM_CODEC, SetCapePacket::handle);
        registrar.playToClient(SyncCapePacket.TYPE, SyncCapePacket.STREAM_CODEC, SyncCapePacket::handle);
    }

    public static void syncCape(ServerPlayer player) {
        SyncCapePacket packet = new SyncCapePacket(player.getUUID(), NLCapeManager.getSelected(player.getUUID()));

        PacketDistributor.sendToPlayer(player, packet);
        PacketDistributor.sendToPlayersTrackingEntity(player, packet);
    }
}