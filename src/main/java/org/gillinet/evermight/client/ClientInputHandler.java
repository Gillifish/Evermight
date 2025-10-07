// org/gillinet/evermight/client/ClientInputHandler.java
package org.gillinet.evermight.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.gillinet.evermight.Evermight;
import org.gillinet.evermight.EvermightKeyMapping;
import org.gillinet.evermight.network.CastSpellC2SPayload;

@EventBusSubscriber(modid = Evermight.MODID, value = Dist.CLIENT)
public final class ClientInputHandler {
    private ClientInputHandler() {}

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post e) {
        if (EvermightKeyMapping.CAST_KEY == null) return;

        while (EvermightKeyMapping.CAST_KEY.consumeClick()) {
            Minecraft mc = Minecraft.getInstance();
            LocalPlayer p = mc.player;
            if (p == null) return;

            // Use the current crosshair hit if available, else 10-block ray ahead
            Vec3 target;
            HitResult hr = mc.hitResult;
            if (hr != null && hr.getType() != HitResult.Type.MISS) {
                target = hr.getLocation();
            } else {
                target = p.getEyePosition().add(p.getViewVector(1.0F).scale(10.0));
            }

            PacketDistributor.sendToServer(new CastSpellC2SPayload(target.x, target.y, target.z));
        }
    }
}
