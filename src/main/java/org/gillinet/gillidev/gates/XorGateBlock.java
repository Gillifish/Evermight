package org.gillinet.gillidev.gates;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.state.BlockState;

public class XorGateBlock extends BaseGateBlock {
    public static final MapCodec<XorGateBlock> CODEC = Block.simpleCodec(XorGateBlock::new);
    public XorGateBlock(Properties props) { super(props); }
    @Override protected MapCodec<? extends DiodeBlock> codec() { return CODEC; }

    @Override
    protected boolean shouldTurnOn(Level level, BlockPos pos, BlockState state) {
        Direction facing = state.getValue(DiodeBlock.FACING);
        int left  = getSideSignal(level, pos, facing.getCounterClockWise());
        int right = getSideSignal(level, pos, facing.getClockWise());
        return left > 0 ^ right > 0;
    }
}
