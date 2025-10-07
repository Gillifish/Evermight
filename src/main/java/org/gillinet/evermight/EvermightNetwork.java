// org/gillinet/evermight/network/NetworkInit.java
package org.gillinet.evermight;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.gillinet.evermight.network.CastSpellC2SPayload;
import org.gillinet.evermight.network.ServerPayloadHandler;

@EventBusSubscriber(modid = Evermight.MODID)
public final class EvermightNetwork {
    private EvermightNetwork() {}

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        PayloadRegistrar r = event.registrar("1");
        r.playToServer(
                CastSpellC2SPayload.TYPE,
                CastSpellC2SPayload.STREAM_CODEC,
                ServerPayloadHandler::handleCastSpell
        );
    }
}
