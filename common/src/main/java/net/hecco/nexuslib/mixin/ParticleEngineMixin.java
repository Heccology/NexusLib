package net.hecco.nexuslib.mixin;

import net.hecco.nexuslib.lib.util.NLParticleRenderTypes;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleRenderType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(ParticleEngine.class)
public class ParticleEngineMixin {
    @Shadow
    @Final
    @Mutable
    private static List<ParticleRenderType> RENDER_ORDER;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void nexuslib$addParticleRenderTypes(CallbackInfo ci) {
        List<ParticleRenderType> order = new ArrayList<>(RENDER_ORDER);
        int customIndex = order.indexOf(ParticleRenderType.CUSTOM);
        order.add(customIndex, NLParticleRenderTypes.PARTICLE_SHEET_CLOUD);
        RENDER_ORDER = order;
    }
}