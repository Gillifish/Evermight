package org.gillinet.evermight;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.gillinet.evermight.weapons.TheShovel;
import org.gillinet.evermight.weapons.TheShovelItem;

public final class EvermightItems {
    // Dedicated Items register for this class (separate from any template registers in GilliDev)
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(Evermight.MODID);

    // Your special trident-like item
    public static final DeferredItem<TheShovelItem> THE_SHOVEL_ITEM =
            ITEMS.register("the_shovel",
                    () -> new TheShovelItem(new Item.Properties()
                            .stacksTo(1)
                            .durability(500)    // tweak as you like
                    )
            );

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }

    private EvermightItems() {}
}