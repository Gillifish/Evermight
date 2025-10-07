// org/gillinet/evermight/network/ServerPayloadHandler.java
package org.gillinet.evermight.network;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class ServerPayloadHandler {
    private ServerPayloadHandler() {}

    public static void handleCastSpell(final CastSpellC2SPayload msg, final IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer sp = (ServerPlayer) ctx.player();
            if (sp == null || sp.isSpectator()) return;

            ServerLevel level = sp.serverLevel();
            Vec3 eye = sp.getEyePosition();
            Vec3 to  = new Vec3(msg.x(), msg.y(), msg.z());
            Vec3 dir = to.subtract(eye).normalize();

            Arrow arrow = new Arrow(EntityType.ARROW, level);
            arrow.setOwner(sp);

            // Spawn slightly in front of the eyes to avoid immediate self-hit
            Vec3 spawn = eye.add(dir.scale(0.15));
            arrow.moveTo(spawn.x, spawn.y, spawn.z);

            arrow.setCritArrow(true);   // crit particles (purely visual)
            arrow.setBaseDamage(5.0D);  // vanilla bow-ish ~2–10; adjust to taste

            // Launch toward the target
            float velocity = 3.0F;      // 3–4 feels bow-like
            float inaccuracy = 0.0F;    // >0 adds spread
            arrow.shoot(dir.x, dir.y, dir.z, velocity, inaccuracy);

            level.addFreshEntity(arrow);
            level.playSound(null, sp.getX(), sp.getY(), sp.getZ(),
                    SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS,
                    1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
        });
    }
}
