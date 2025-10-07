package org.gillinet.evermight;


import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.gillinet.evermight.weapons.TheShovel;

public class EvermightEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, Evermight.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<TheShovel>> THE_SHOVEL =
            ENTITIES.register("the_shovel",
                    () -> EntityType.Builder.<TheShovel>of(TheShovel::new, MobCategory.MISC)
                            .sized(0.5f, 0.5f)
                            .clientTrackingRange(4)
                            .updateInterval(20)
                            .build("evermight:the_shovel")
            );

    public static void register(IEventBus bus) {
        ENTITIES.register(bus);
    }
}