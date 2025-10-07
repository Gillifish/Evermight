package org.gillinet.evermight;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.gillinet.evermight.gates.*;

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

    // Pair BlockItem, registered during the Items phase (safe: Blocks already bound)
    public static final net.neoforged.neoforge.registries.DeferredItem<BlockItem> AND_GATE_ITEM =
            ITEMS.register("and_gate", () -> new BlockItem(AND_GATE.get(), new Item.Properties()));

    public static final net.neoforged.neoforge.registries.DeferredItem<BlockItem> OR_GATE_ITEM =
            ITEMS.register("or_gate", () -> new BlockItem(OR_GATE.get(), new Item.Properties()));

    public static final net.neoforged.neoforge.registries.DeferredItem<BlockItem> XOR_GATE_ITEM =
            ITEMS.register("xor_gate", () -> new BlockItem(XOR_GATE.get(), new Item.Properties()));

    public static final net.neoforged.neoforge.registries.DeferredItem<BlockItem> NOT_GATE_ITEM =
            ITEMS.register("not_gate", () -> new BlockItem(NOT_GATE.get(), new Item.Properties()));

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
        ITEMS.register(bus);
    }
}
