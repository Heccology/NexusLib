package net.hecco.nexuslib;

import net.hecco.nexuslib.lib.cape.SetCapePacket;
import net.hecco.nexuslib.lib.paletteTemplateMatrixSpriteSource.NLSpriteSources;
import net.hecco.nexuslib.lib.paletteTemplateMatrixSpriteSource.PaletteTemplateMatrix;
import net.hecco.nexuslib.lib.util.NLCapeManager;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RegisterSpriteSourceTypesEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = NexusLib.MOD_ID, value = Dist.CLIENT)
public class NexusLibClientNeoForge {
    @SubscribeEvent
    public static void onJoin(ClientPlayerNetworkEvent.LoggingIn event) {
        Minecraft minecraft = Minecraft.getInstance();

        minecraft.execute(() -> {
            if (minecraft.player == null) return;
            PacketDistributor.sendToServer(new SetCapePacket(NLCapeManager.getSelected(minecraft.player.getUUID())));
        });
    }

    @SubscribeEvent
    public static void onRegisterSpriteSources(RegisterSpriteSourceTypesEvent event) {
        event.register(NexusLib.id("palette_template_matrix"), NLSpriteSources.PALETTE_TEMPLATE_MATRIX);
    }
}