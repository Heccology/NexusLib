package net.hecco.nexuslib.platform;

import net.hecco.nexuslib.platform.services.NLNetworkingHelper;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

public class NeoForgeNetworkingHelper implements NLNetworkingHelper {
    @Override
    public void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {
        PacketDistributor.sendToPlayer(player, payload);
    }

    @Override
    public void sentToServer(CustomPacketPayload payload) {
        PacketDistributor.sendToServer(payload);
    }
}
