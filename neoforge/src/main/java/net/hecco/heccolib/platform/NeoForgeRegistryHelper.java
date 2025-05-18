package net.hecco.heccolib.platform;

import net.hecco.heccolib.platform.services.HLRegistryHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class NeoForgeRegistryHelper implements HLRegistryHelper {
    IEventBus modEventBus = ModLoadingContext.get().getActiveContainer().getEventBus();
//
//    private final DeferredRegister<Block> blocks;
//    private final DeferredRegister<Item> items;
//
//    public NeoForgeRegistryHelper(String modid) {
//        this.blocks = DeferredRegister.create(Registries.BLOCK, modid);
//        this.items = DeferredRegister.create(Registries.ITEM, modid);
//        blocks.register(modEventBus);
//        items.register(modEventBus);
//    }
//
//    public NeoForgeRegistryHelper() {
//        this.blocks = null;
//        this.items = null;
//    }

//    @Override
//    public <T extends Block> Supplier<T> registerBlock(String modid, String id, Supplier<T> supplier) {
//        return blocks.register(id, supplier);
//    }
    @Override
    public <T extends Block> Supplier<T> registerBlockNoItem(String modid, String id, Supplier<T> supplier) {
        DeferredRegister<Block> blockDeferredRegister = DeferredRegister.create(Registries.BLOCK, modid);
        DeferredRegister<Item> itemDeferredRegister = DeferredRegister.create(Registries.ITEM, modid);
        DeferredHolder<Block, T> blockRegister = blockDeferredRegister.register(id, supplier);

        blockDeferredRegister.register(this.modEventBus);
        itemDeferredRegister.register(this.modEventBus);
        return blockRegister;
    }

    @Override
    public <T extends Block> Supplier<T> registerBlock(String modid, String id, Supplier<T> supplier) {
        DeferredRegister<Block> blockDeferredRegister = DeferredRegister.create(Registries.BLOCK, modid);
        DeferredRegister<Item> itemDeferredRegister = DeferredRegister.create(Registries.ITEM, modid);
        DeferredHolder<Block, T> blockRegister = blockDeferredRegister.register(id, supplier);
        itemDeferredRegister.register(id, () -> new BlockItem(blockRegister.get(), new Item.Properties()));
        blockDeferredRegister.register(this.modEventBus);
        itemDeferredRegister.register(this.modEventBus);
        return blockRegister;
    }
}
