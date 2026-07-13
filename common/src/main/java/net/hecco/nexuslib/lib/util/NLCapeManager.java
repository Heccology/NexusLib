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
        add(UUID.fromString("380df991-f603-344c-a090-369bad2a924a"), //Dev
                cape("nexuslib"),
                cape("bountifulfares"),
                cape("bountifulfares_super"),
                cape("subterrous"),
                cape("subterrous_super"),
                cape("feldspar")
        );

        add(UUID.fromString("1cedf927-5c8f-4650-95e9-808fc8f94d00"), //Yirmiri
                cape("nexuslib"),
                cape("bountifulfares"),
                cape("bountifulfares_super"),
                cape("subterrous"),
                cape("subterrous_super"),
                cape("feldspar")
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
                cape("subterrous_super"),
                cape("feldspar")
        );

        add(UUID.fromString("d1dac9fe-3ef0-4ea8-997b-b7cdd6a92131"), //arid_lizzy
                cape("bountifulfares"),
                cape("feldspar")
        );

        add(UUID.fromString("32290fa8-77ed-4794-9cba-25c09e7f4e1d"), //Diemond_Player
                cape("bountifulfares"),
                cape("bountifulfares_super"),
                cape("feldspar")
        );

        add(UUID.fromString("385f22c1-4661-4982-b024-80996b0edbc5"), //WorkGoblin
                cape("bountifulfares"),
                cape("subterrous")
        );

        add(UUID.fromString("9778ff53-d83d-4233-8fa6-8aab7b89c4c0"), //Stellari_
                cape("bountifulfares")
        );

        add(UUID.fromString("64f0361c-a7a8-43ec-83c4-378f8e28702c"), //wool___
                cape("bountifulfares")
        );

        add(UUID.fromString("fc3f8da9-57d6-42df-a12c-34c3cb543d46"), //STRANGEPARTYZ
                cape("bountifulfares"),
                cape("subterrous")
        );

        add(UUID.fromString("b110eaf9-60c1-4427-9a99-950dadceec08"), //Lucifer_Starrr
                cape("bountifulfares")
        );

        add(UUID.fromString("af5c5305-b430-43ac-8d64-117e469faf05"), //JuliyJuliusMC
                cape("bountifulfares"),
                cape("subterrous")
        );

        add(UUID.fromString("bea56b09-adb3-4da8-831a-0016b56319a7"), //Felixe_
                cape("bountifulfares")
        );


        //Subterrous Playtesters

        add(UUID.fromString("512751b0-481a-471d-85b7-9bfba10a016d"), //cwdotpng
                cape("subterrous")
        );
        add(UUID.fromString("ea114f80-3cec-4abb-b904-0d20716cb2d4"), //PMania
                cape("subterrous")
        );
        add(UUID.fromString("3562ab33-f01b-4801-aab5-807f3750ded1"), //AbysswalkerDeno
                cape("subterrous")
        );
        add(UUID.fromString("c1be3aa5-e9e6-42c0-a47d-f981f4cb63e4"), //AleXsander69
                cape("subterrous")
        );
        add(UUID.fromString("1fb0ecbd-2000-4f00-aa65-002c1ccedef7"), //StarlitStelle
                cape("subterrous")
        );
        add(UUID.fromString("5cc8b9ad-b10e-4a5d-9ae8-33247502497d"), //Hyperforg
                cape("subterrous")
        );
        add(UUID.fromString("7809cf0d-23c1-47e9-a366-9b023cae1583"), //ClephLeSDF
                cape("subterrous")
        );
        add(UUID.fromString("f1ec4cf4-584e-4b5b-a59a-fcfdf4a8bba1"), //kemiu
                cape("subterrous")
        );
        add(UUID.fromString("692bc640-c258-4a9c-8a71-bc1e773083ac"), //chlowey_
                cape("subterrous")
        );
        add(UUID.fromString("2dd03cc0-baff-4998-ade8-db0363d881d1"), //Jasdernn
                cape("subterrous")
        );
        add(UUID.fromString("4c536f4a-80b1-40d7-be0f-bec603e3889d"), //Silviodozen
                cape("subterrous")
        );
        add(UUID.fromString("85cf85df-3c15-40c1-b0fa-390f45e4e6a7"), //Kontiolax
                cape("subterrous")
        );
        add(UUID.fromString("1e36cd2b-e564-4cc4-90e0-d05d6dc55df8"), //GrayAlex226
                cape("subterrous")
        );
        add(UUID.fromString("f134adfe-b882-4260-ae14-4282e8fc84a0"), //Mosca__
                cape("subterrous")
        );
        add(UUID.fromString("f1df9144-dbd9-413b-bd10-1f92f409c622"), //EnderKTS
                cape("subterrous")
        );
        add(UUID.fromString("64420135-651e-4219-97d0-c2c80eab9046"), //thenatezi11a
                cape("subterrous")
        );
        add(UUID.fromString("d99f4d54-7301-4952-85a5-f2844ad608d6"), //Derp_gamer22
                cape("subterrous")
        );
        add(UUID.fromString("aa2f9826-4137-4f77-8817-7bc37d2f0cf7"), //cucuber_321
                cape("subterrous")
        );
        add(UUID.fromString("24f93698-b326-4c4d-991d-24c424438a1d"), //estarlightp
                cape("subterrous")
        );
        add(UUID.fromString("054cc975-2e89-4071-b27d-bb955ff48585"), //Grae2016
                cape("subterrous")
        );
        add(UUID.fromString("b80e0524-dd94-4246-a611-d4ab81e7647d"), //Soul_bolt
                cape("subterrous")
        );
        add(UUID.fromString("90d46a0f-0154-410c-9f78-4ff2c8d0780a"), //Sp71ng
                cape("subterrous")
        );
        add(UUID.fromString("e5f583c3-c3ea-4131-86b8-da100024c035"), //Simeeow
                cape("subterrous")
        );
        add(UUID.fromString("eed3424a-d3a1-412f-8664-e528da458139"), //HatsuneTreeku
                cape("subterrous")
        );
        add(UUID.fromString("348bdf55-217e-4b47-80e7-1615172726df"), //TancrebitPC
                cape("subterrous")
        );
        add(UUID.fromString("08e81c68-0a2f-4625-882b-ddcf3c86e351"), //astralverdance
                cape("subterrous")
        );
        add(UUID.fromString("5a603688-39ea-46e5-a368-529d98c32363"), //FilenameInfinity
                cape("subterrous")
        );
        add(UUID.fromString("ea114f80-3cec-4abb-b904-0d20716cb2d4"), //P_Mania
                cape("subterrous")
        );
        add(UUID.fromString("a7727c38-0417-4314-8d31-95b4354a3ced"), //LEM0N4643
                cape("subterrous")
        );
        add(UUID.fromString("83dfce82-3f69-4ae8-b661-4c2e6e0b7f6f"), //miloq__
                cape("subterrous")
        );
        add(UUID.fromString("f1aafe5c-4c4c-4287-9f14-dfda7f51750f"), //gnorki
                cape("subterrous")
        );
        add(UUID.fromString("e8bedbca-9ae2-44a1-8bca-1d50e1323bdc"), //Minecraft_F4N
                cape("subterrous")
        );
        add(UUID.fromString("d9a83ea1-d639-4164-8477-77c3c51062c0"), //TentenSwift
                cape("subterrous")
        );
        add(UUID.fromString("20b286e7-c271-442f-a0b2-ecae09575d30"), //AnoKonstella
                cape("subterrous")
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

    public static void load() {
        if (!Files.exists(FILE)) return;
        Minecraft minecraft = Minecraft.getInstance();

        try (Reader reader = Files.newBufferedReader(FILE)) {
            Integer selected = GSON.fromJson(reader, Integer.class);

            if (selected == null) return;

            SELECTED.clear();
            SELECTED.put(minecraft.getUser().getProfileId(), selected);
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