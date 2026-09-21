package net.hecco.nexuslib.lib.situationalMusicRegistry;

import com.mojang.datafixers.util.Function4;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.world.level.biome.Biome;

import java.util.LinkedHashMap;
import java.util.Map;

public class NLSituationalMusicRegistry {
    private static final Map<Function4<MusicManager, ClientLevel, LocalPlayer, Holder<Biome>, Boolean>, Music> REGISTRY = new LinkedHashMap<>();

    public static void registerSituationalMusic(Function4<MusicManager, ClientLevel, LocalPlayer, Holder<Biome>, Boolean> condition, Music music) {
        REGISTRY.put(condition, music);
    }

    public static Map<Function4<MusicManager, ClientLevel, LocalPlayer, Holder<Biome>, Boolean>, Music> getEntries() {
        return REGISTRY;
    }
}
