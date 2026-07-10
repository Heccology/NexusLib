package net.hecco.nexuslib.lib.cape;

import net.hecco.nexuslib.NexusLib;
import net.hecco.nexuslib.lib.util.NLCapeManager;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.UUID;

public record SyncCapePacket(UUID player, int index) implements CustomPacketPayload {
    public static final Type<SyncCapePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(NexusLib.MOD_ID, "sync_cape"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncCapePacket> STREAM_CODEC = StreamCodec.of((buffer, packet) -> {
        buffer.writeUUID(packet.player());buffer.writeInt(packet.index());
        }, buffer -> new SyncCapePacket(buffer.readUUID(), buffer.readInt()));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SyncCapePacket packet, IPayloadContext ctx) {
        ctx.enqueueWork(() -> NLCapeManager.setSelected(packet.player(), packet.index()));
    }
}