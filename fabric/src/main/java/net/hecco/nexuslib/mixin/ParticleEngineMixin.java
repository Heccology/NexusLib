package net.hecco.nexuslib.mixin;

import net.hecco.nexuslib.lib.util.NLParticleRenderTypes;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleRenderType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.List;

@Mixin(ParticleEngine.class)
public class ParticleEngineMixin {
    @Shadow
    @Final
    @Mutable
    private static List<ParticleRenderType> RENDER_ORDER;

    static {
        RENDER_ORDER = new ArrayList<>(RENDER_ORDER);
        RENDER_ORDER.add(NLParticleRenderTypes.PARTICLE_SHEET_CLOUD);
    }
}
