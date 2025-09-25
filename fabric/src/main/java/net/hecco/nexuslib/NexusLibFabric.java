package net.hecco.nexuslib;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.Minecraft;

public class NexusLibFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        NexusLib.init();

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            Minecraft.getInstance().gameRenderer.checkEntityPostEffect(client.player);
        });
    }
}
