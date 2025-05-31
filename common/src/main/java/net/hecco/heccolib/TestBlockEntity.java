package net.hecco.heccolib;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TestBlockEntity extends BlockEntity {
    public int count = 0;
    public TestBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.TEST.get(), pos, blockState);
    }
}
