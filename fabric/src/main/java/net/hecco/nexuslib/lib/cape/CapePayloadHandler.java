package net.hecco.nexuslib.lib.cape;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.hecco.nexuslib.lib.util.NLCapeManager;
import net.minecraft.server.level.ServerPlayer;

public final class CapePayloadHandler {
    public static void register() {
        PayloadTypeRegistry.playC2S().register(SetCapePacket.TYPE, SetCapePacket.STREAM_CODEC);
        PayloadTypeRegistry.playS2C().register(SyncCapePacket.TYPE, SyncCapePacket.STREAM_CODEC);
    }

    public static void syncCape(ServerPlayer player) {
        SyncCapePacket packet = new SyncCapePacket(player.getUUID(), NLCapeManager.getSelected(player.getUUID()));
        ServerPlayNetworking.send(player, packet);

        for (ServerPlayer other : player.serverLevel().players()) {
            if (other != player) {
                ServerPlayNetworking.send(other, packet);
            }
        }
    }
}