package net.hecco.nexuslib.lib.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.hecco.nexuslib.NexusLib;
import net.hecco.nexuslib.platform.NLServices;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class NLCapeManager {
    private static final Map<UUID, List<ResourceLocation>> CAPES = new HashMap<>();
    private static final Map<UUID, Integer> SELECTED = new HashMap<>();
    private static final Map<ResourceLocation, String> NAMES = new HashMap<>();
    private static final Path FILE = NLServices.PLATFORM.gameDir().resolve("hec_cape.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    static {
        add(UUID.fromString("1cedf927-5c8f-4650-95e9-808fc8f94d00"), //Yirmiri
                cape("nexuslib"),
                cape("bountifulfares"),
                cape("bountifulfares_super"),
                cape("subterrous"),
                cape("subterrous_super")
        );

        add(UUID.fromString("bc56b2c8-9ef8-4532-b045-00f44804bca4"), //TheHecco
                cape("nexuslib"),
                cape("bountifulfares"),
                cape("bountifulfares_super"),
                cape("subterrous"),
                cape("subterrous_super")
        );

        add(UUID.fromString("774e37fc-1ca4-4156-827e-661afa24cb56"), //_Artyrian
                cape("nexuslib"),
                cape("bountifulfares"),
                cape("bountifulfares_super"),
                cape("subterrous"),
                cape("subterrous_super")
        );

        add(UUID.fromString("d1dac9fe-3ef0-4ea8-997b-b7cdd6a92131"), //arid_lizzy
                cape("bountifulfares")
        );

        add(UUID.fromString("32290fa8-77ed-4794-9cba-25c09e7f4e1d"), //Diemond_Player
                cape("bountifulfares"),
                cape("bountifulfares_super")
        );

        add(UUID.fromString("385f22c1-4661-4982-b024-80996b0edbc5"), //WorkGoblin
                cape("bountifulfares")
        );

        add(UUID.fromString("9778ff53-d83d-4233-8fa6-8aab7b89c4c0"), //Stellari_
                cape("bountifulfares")
        );

        add(UUID.fromString("64f0361c-a7a8-43ec-83c4-378f8e28702c"), //wool___
                cape("bountifulfares")
        );

        add(UUID.fromString("fc3f8da9-57d6-42df-a12c-34c3cb543d46"), //STRANGEPARTYZ
                cape("bountifulfares")
        );

        add(UUID.fromString("b110eaf9-60c1-4427-9a99-950dadceec08"), //Lucifer_Starrr
                cape("bountifulfares")
        );

        add(UUID.fromString("af5c5305-b430-43ac-8d64-117e469faf05"), //JuliyJuliusMC
                cape("bountifulfares")
        );

        add(UUID.fromString("bea56b09-adb3-4da8-831a-0016b56319a7"), //Felixe_
                cape("bountifulfares")
        );

        load();
    }

    private static ResourceLocation cape(String id) {
        ResourceLocation texture = NexusLib.modid("textures/capes/" + id + ".png");
        NAMES.put(texture, "nexuslib.cape." + id);
        return texture;
    }

    private static void add(UUID uuid, ResourceLocation... capes) {
        CAPES.put(uuid, List.of(capes));
    }

    public static List<ResourceLocation> getCapes(UUID uuid) {
        return CAPES.getOrDefault(uuid, List.of());
    }

    public static ResourceLocation getCape(UUID uuid) {
        List<ResourceLocation> capes = getCapes(uuid);
        if (capes.isEmpty()) return null;

        int selected = SELECTED.getOrDefault(uuid, -1);
        if (selected < 0) return null;
        if (selected >= capes.size()) selected = 0;

        return capes.get(selected);
    }

    public static int getSelected(UUID uuid) {
        return SELECTED.getOrDefault(uuid, -1);
    }

    public static void setSelected(UUID uuid, int selected) {
        SELECTED.put(uuid, selected);
        save();
    }

    public static int next(UUID uuid) {
        List<ResourceLocation> capes = getCapes(uuid);
        if (capes.isEmpty()) return -1;

        int selected = getSelected(uuid);
        selected++;

        if (selected >= capes.size()) selected = -1;

        setSelected(uuid, selected);
        return selected;
    }

    public static Component getSelectedName(UUID uuid) {
        ResourceLocation texture = getCape(uuid);
        if (texture == null) return Component.translatable("nexuslib.cape.vanilla");

        return Component.translatable(NAMES.get(texture));
    }

    private static void load() {
        if (!Files.exists(FILE)) return;

        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) return;

        try (Reader reader = Files.newBufferedReader(FILE)) {
            Integer selected = GSON.fromJson(reader, Integer.class);

            if (selected == null) return;

            SELECTED.clear();
            SELECTED.put(minecraft.player.getUUID(), selected);
        } catch (IOException ignored) {
        }
    }

    private static void save() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) return;

        try (Writer writer = Files.newBufferedWriter(FILE)) {
            GSON.toJson(SELECTED.getOrDefault(minecraft.player.getUUID(), -1), writer);
        } catch (IOException ignored) {
        }
    }
}