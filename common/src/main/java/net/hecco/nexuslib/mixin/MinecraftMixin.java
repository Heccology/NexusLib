package net.hecco.nexuslib.mixin;

import com.mojang.datafixers.util.Function4;
import net.hecco.nexuslib.lib.situationalMusicRegistry.NLSituationalMusicRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow @Nullable
    public ClientLevel level;

    @Shadow @Nullable public LocalPlayer player;

    @Shadow @Final private MusicManager musicManager;

    @Inject(method = "getSituationalMusic", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBiome(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/Holder;", shift = At.Shift.AFTER), cancellable = true)
    private void nexuslib$situationalMusicRegistry(CallbackInfoReturnable<Music> cir) {
        Holder<Biome> holder = player.level().getBiome(player.blockPosition());
        for (Map.Entry<Function4<MusicManager, ClientLevel, LocalPlayer, Holder<Biome>, Boolean>, Music> entry : NLSituationalMusicRegistry.getEntries().entrySet()) {
            if (entry.getKey().apply(musicManager, level, player, holder)) {
                cir.setReturnValue(entry.getValue());
            }
        }
    }
}
