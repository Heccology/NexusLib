package net.hecco.heccolib.platform.services;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public interface HLRegistryHelper {

    <T extends Block> Supplier<T> registerBlockNoItem(String modid, String id, Supplier<T> supplier);

    <T extends Block> Supplier<T> registerBlock(String modid, String id, Supplier<T> supplier);

}
