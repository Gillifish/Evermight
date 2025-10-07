package org.gillinet.evermight;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.gillinet.evermight.weapons.TheShovel;
import org.gillinet.evermight.weapons.TheShovelItem;

import static org.gillinet.evermight.EvermightBlocks.*;

public class EvermightItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(Evermight.MODID);

    public static final DeferredItem<TheShovelItem> THE_SHOVEL_ITEM =
            ITEMS.register("the_shovel",
                    () -> new TheShovelItem(new Item.Properties()
                            .stacksTo(1)
                            .durability(500)    // tweak as you like
                    )
            );

    public static final net.neoforged.neoforge.registries.DeferredItem<BlockItem> AND_GATE_ITEM =
            ITEMS.register("and_gate_item", () -> new BlockItem(AND_GATE.get(), new Item.Properties()));

    public static final net.neoforged.neoforge.registries.DeferredItem<BlockItem> OR_GATE_ITEM =
            ITEMS.register("or_gate_item", () -> new BlockItem(OR_GATE.get(), new Item.Properties()));

    public static final net.neoforged.neoforge.registries.DeferredItem<BlockItem> XOR_GATE_ITEM =
            ITEMS.register("xor_gate_item", () -> new BlockItem(XOR_GATE.get(), new Item.Properties()));

    public static final net.neoforged.neoforge.registries.DeferredItem<BlockItem> NOT_GATE_ITEM =
            ITEMS.register("not_gate_item", () -> new BlockItem(NOT_GATE.get(), new Item.Properties()));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }

    private EvermightItems() {}
}