package org.gillinet.evermight.gates;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;

public abstract class BaseGateBlock extends DiodeBlock {
    protected BaseGateBlock(BlockBehaviour.Properties props) {
        super(props);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(DiodeBlock.FACING, Direction.NORTH)).setValue(DiodeBlock.POWERED, false));

        assert this.getStateDefinition().getProperties().contains(DiodeBlock.FACING) : "FACING missing";

        assert this.getStateDefinition().getProperties().contains(DiodeBlock.POWERED) : "POWERED missing";

    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{DiodeBlock.FACING, DiodeBlock.POWERED});
    }

    protected abstract MapCodec<? extends DiodeBlock> codec();

    protected int getDelay(BlockState state) {
        return 1;
    }

    protected abstract boolean shouldTurnOn(Level var1, BlockPos var2, BlockState var3);

    protected int getOutputSignal(BlockGetter level, BlockPos pos, BlockState state) {
        return (Boolean)state.getValue(DiodeBlock.POWERED) ? 15 : 0;
    }

    public static int getSideSignal(Level level, BlockPos pos, Direction sideDir) {
        BlockPos neighbor = pos.relative(sideDir);

        // Primary: signal that the neighbor outputs *toward us*.
        int signal = level.getSignal(neighbor, sideDir.getOpposite());
        if (signal >= 15) return signal;

        // Dust fallback (redstone wire outputs its level but not always via getSignal):
        BlockState ns = level.getBlockState(neighbor);
        if (ns.is(Blocks.REDSTONE_WIRE)) {
            signal = Math.max(signal, ns.getValue(RedStoneWireBlock.POWER));
        }
        return signal;
    }
}
