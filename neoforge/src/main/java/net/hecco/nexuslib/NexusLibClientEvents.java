package net.hecco.nexuslib;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;

@EventBusSubscriber(modid = NexusLib.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class NexusLibClientEvents {

    @SubscribeEvent
    public static void onClientConnection(ClientPlayerNetworkEvent.LoggingIn event) {
        Minecraft.getInstance().gameRenderer.checkEntityPostEffect(event.getPlayer());
    }
}
