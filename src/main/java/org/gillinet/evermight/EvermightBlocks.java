package org.gillinet.evermight;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.gillinet.evermight.gates.*;

import static org.gillinet.evermight.EvermightEntities.ENTITIES;

public class EvermightBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Evermight.MODID);
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(Evermight.MODID);

    public static final DeferredBlock<AndGateBlock> AND_GATE = BLOCKS.register("and_gate",
            () -> new AndGateBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .strength(0.5f)
                    .noOcclusion()));

    public static final DeferredBlock<OrGateBlock> OR_GATE = BLOCKS.register("or_gate",
            () -> new OrGateBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .strength(0.5f)
                    .noOcclusion()));

    public static final DeferredBlock<XorGateBlock> XOR_GATE = BLOCKS.register("xor_gate",
            () -> new XorGateBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .strength(0.5f)
                    .noOcclusion()));

    public static final DeferredBlock<NotGateBlock> NOT_GATE = BLOCKS.register("not_gate",
            () -> new NotGateBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .strength(0.5f)
                    .noOcclusion()));

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
