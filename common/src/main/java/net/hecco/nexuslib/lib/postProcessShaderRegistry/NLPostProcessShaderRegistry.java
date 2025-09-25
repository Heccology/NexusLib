package net.hecco.nexuslib.lib.postProcessShaderRegistry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import oshi.util.tuples.Pair;

import java.util.*;
import java.util.function.Function;

public class NLPostProcessShaderRegistry {
    private static final Map<Function<Entity, Boolean>, ResourceLocation> SHADERS = new HashMap<>();
    public static final Map<String, Boolean> SHADER_TO_TOGGLEABLE = new HashMap<>();

    public static void add(Function<Entity, Boolean> condition, ResourceLocation shader, Boolean canBeToggled) {
        SHADERS.put(condition, shader);
        SHADER_TO_TOGGLEABLE.put(shader.toString(), canBeToggled);
    }

    public static Map<Function<Entity, Boolean>, ResourceLocation> getShaders() {
        return SHADERS;
    }

}
