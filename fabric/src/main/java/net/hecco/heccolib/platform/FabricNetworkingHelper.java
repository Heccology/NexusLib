package net.hecco.heccolib.platform;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.hecco.heccolib.platform.services.HLNetworkingHelper;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public class FabricNetworkingHelper implements HLNetworkingHelper {
    @Override
    public void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {
        ServerPlayNetworking.send(player, payload);
    }
}
