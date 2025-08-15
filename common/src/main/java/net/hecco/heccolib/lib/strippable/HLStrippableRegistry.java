package net.hecco.heccolib.lib.strippable;

import net.hecco.heccolib.HeccoLib;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class HLStrippableRegistry {
    private static final Map<Block, Block> LOGS = new LinkedHashMap<>();

    public static void add(Block log, Block stripped) {
        boolean noLogAxis = !log.defaultBlockState().hasProperty(RotatedPillarBlock.AXIS);
        boolean noStripAxis = !stripped.defaultBlockState().hasProperty(RotatedPillarBlock.AXIS);

        if (noLogAxis || noStripAxis) {
            String output;
            if (noLogAxis && noStripAxis) output = "both";
            else output = (noLogAxis) ? log.toString() : stripped.toString();

            HeccoLib.LOGGER.error("Could not register axe stripping behavior for {} and {} due to a missing axis property in {}!", log, stripped, output);
        }
        else {
            LOGS.put(log, stripped);
        }
    }

    public static void add(Supplier<Block> log, Supplier<Block> stripped) {
        add(log.get(), stripped.get());
    }

    public static Map<Block, Block> getStrippables() { return LOGS; }
}
