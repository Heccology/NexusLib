package net.hecco.heccolib.platform.services;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;

public interface HLNetworkingHelper {
    void sendToPlayer(ServerPlayer player, CustomPacketPayload payload);

    default void sendToPlayersTrackingChunk(ServerLevel level, BlockPos pos, CustomPacketPayload payload) {
        for (ServerPlayer targeter : level.getChunkSource().chunkMap.getPlayers(new ChunkPos(pos), false)) {
            sendToPlayer(targeter, payload);
        }
    }
}
