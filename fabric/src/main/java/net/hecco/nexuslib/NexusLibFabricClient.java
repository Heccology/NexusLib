package net.hecco.nexuslib;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.hecco.nexuslib.lib.cape.SetCapePacket;
import net.hecco.nexuslib.lib.cape.SyncCapePacket;
import net.hecco.nexuslib.lib.util.NLCapeManager;

public class NexusLibFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(SyncCapePacket.TYPE, SyncCapePacket::handle);

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> client.execute(() -> {
            if (client.player == null) return;
            ClientPlayNetworking.send(new SetCapePacket(NLCapeManager.getSelected(client.player.getUUID())));
        }));
    }
}
