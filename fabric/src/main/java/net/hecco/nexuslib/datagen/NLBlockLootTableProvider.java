package net.hecco.nexuslib.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.hecco.nexuslib.lib.util.NLUtility;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public abstract class NLBlockLootTableProvider extends FabricBlockLootTableProvider {

    private final String MOD_ID;

    public NLBlockLootTableProvider(String modId, FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
        this.MOD_ID = modId;
    }

    public static final ArrayList<Block> usedBlocks = new ArrayList<>();

    @Override
    public void add(Block block, LootTable.Builder lootTable) {
        if(usedBlocks.contains(block)) {
            return;
        }
        super.add(block, lootTable);
        usedBlocks.add(block);
    }

    public void generateDefaultLootTables() {
        for(ResourceLocation id : NLUtility.allBlockIdsInNamespace(this.MOD_ID)) {
            Block block = BuiltInRegistries.BLOCK.get(id);
            if(usedBlocks.contains(block)) { continue; }
            if (block instanceof SlabBlock) {
                this.add(block, createSlabItemTable(block));
            } else {
                this.dropSelf(block);
            }
        }
    }
}
