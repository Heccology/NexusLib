package net.hecco.nexuslib;

import net.hecco.nexuslib.lib.cape.SyncCapePacket;
import net.hecco.nexuslib.lib.util.NLCapeManager;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = NexusLib.MOD_ID)
public class NexusLibEventsNeoForge {
    @SubscribeEvent
    public static void onJoinEvent(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        for (ServerPlayer other : player.serverLevel().players()) {
            PacketDistributor.sendToPlayer(player, new SyncCapePacket(other.getUUID(), NLCapeManager.getSelected(other.getUUID())));

            if (other != player) {
                PacketDistributor.sendToPlayer(other, new SyncCapePacket(player.getUUID(), NLCapeManager.getSelected(player.getUUID())));
            }
        }
    }
}
