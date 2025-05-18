package net.hecco.heccolib.platform;

import net.hecco.heccolib.HeccoLib;
import net.hecco.heccolib.platform.services.HLRegistryHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class FabricRegistryHelper implements HLRegistryHelper {
    @Override
    public <T extends Block> Supplier<T> registerBlockNoItem(String modid, String id, Supplier<T> supplier) {
        T block = Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(modid, id), supplier.get());
        HeccoLib.LOGGER.info(block.toString());
        return () -> block;
    }

    @Override
    public <T extends Block> Supplier<T> registerBlock(String modid, String id, Supplier<T> supplier) {
        T block = Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(modid, id), supplier.get());
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(modid, id), new BlockItem(block, new Item.Properties()));
        return () -> block;
    }
}
