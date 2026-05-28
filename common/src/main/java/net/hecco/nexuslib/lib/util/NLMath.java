package net.hecco.nexuslib.lib.util;

public class NLMath {
    public static double berp(float delta, float start, float end, float easing) {
        return Math.clamp(end * Math.pow(Math.sin(Math.PI*delta), Math.max(easing / 10, 0)) + start, 0, 1);
    }
}
