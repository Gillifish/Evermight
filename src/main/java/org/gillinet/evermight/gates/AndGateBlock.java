package org.gillinet.evermight.gates;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.state.BlockState;

public class AndGateBlock extends BaseGateBlock {
    public static final MapCodec<AndGateBlock> CODEC = Block.simpleCodec(AndGateBlock::new);
    public AndGateBlock(Properties props) { super(props); }
    @Override protected MapCodec<? extends DiodeBlock> codec() { return CODEC; }

    @Override
    protected boolean shouldTurnOn(Level level, BlockPos pos, BlockState state) {
        Direction facing = state.getValue(DiodeBlock.FACING);
        int left  = getSideSignal(level, pos, facing.getCounterClockWise());
        int right = getSideSignal(level, pos, facing.getClockWise());
        return left > 0 && right > 0;
    }
}