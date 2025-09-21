package net.hecco.nexuslib.lib.defaultAttributeRegistry;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class NLDefaultAttributeRegistry {
    private static final Map<Supplier<?>, AttributeSupplier> SUPPLIERS = new HashMap<>();

    public static void add(Supplier<?> entityType, AttributeSupplier attributes) {
        SUPPLIERS.put(entityType, attributes);
    }

    public static Map<Supplier<?>, AttributeSupplier> getValues() {
        return SUPPLIERS;
    }
}
