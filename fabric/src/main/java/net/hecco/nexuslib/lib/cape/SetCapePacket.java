package net.hecco.nexuslib.lib.cape;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.hecco.nexuslib.NexusLib;
import net.hecco.nexuslib.lib.util.NLCapeManager;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public record SetCapePacket(int index) implements CustomPacketPayload {
    public static final Type<SetCapePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(NexusLib.MOD_ID, "set_cape"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SetCapePacket> STREAM_CODEC = StreamCodec.of((buffer, packet) -> buffer.writeInt(packet.index()), buffer -> new SetCapePacket(buffer.readInt()));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SetCapePacket packet, ServerPlayNetworking.Context ctx) {
        ServerPlayer player = ctx.player();
        ctx.server().execute(() -> {
            int index = packet.index();
            if (index < -1) return;
            if (index >= NLCapeManager.getCapes(player.getUUID()).size()) return;

            NLCapeManager.setSelected(player.getUUID(), index);
            CapePayloadHandler.syncCape(player);
        });
    }
}