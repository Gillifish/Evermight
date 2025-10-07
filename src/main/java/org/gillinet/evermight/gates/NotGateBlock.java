package org.gillinet.evermight.gates;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.state.BlockState;

public class NotGateBlock extends BaseGateBlock {
    public static final MapCodec<NotGateBlock> CODEC = Block.simpleCodec(NotGateBlock::new);
    public NotGateBlock(Properties props) { super(props); }
    @Override protected MapCodec<? extends DiodeBlock> codec() { return CODEC; }

    @Override
    protected boolean shouldTurnOn(Level level, BlockPos pos, BlockState state) {
        int back = getInputSignal(level, pos, state);
        return back == 0;
    }
}