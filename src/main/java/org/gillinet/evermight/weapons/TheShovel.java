package org.gillinet.evermight.weapons;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.gillinet.evermight.EvermightEntities;
import org.gillinet.evermight.EvermightItems;

public class TheShovel extends ThrownTrident {
    private boolean struck;
    private boolean detonated;

    public TheShovel(EntityType<? extends TheShovel> type, Level level) {
        super(type, level);
    }

    /** Pass the stack so loyalty/enchants/name/foil carry onto the projectile. */
    public TheShovel(Level level, LivingEntity owner, ItemStack stack) {
        super(level, owner, stack);
    }

    @Override
    protected void onHitEntity(EntityHitResult hit) {
        super.onHitEntity(hit); // keep normal trident damage & enchants
        if (!level().isClientSide) {
            strikeLightning(hit.getLocation());
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult hit) {
        super.onHitBlock(hit);
        if (!level().isClientSide) {
            // strike at impact point or the block top center—your call
            Vec3 p = Vec3.atCenterOf(hit.getBlockPos());
            strikeLightning(p);
            boom(hit.getLocation(), 5.0F, false, false);
        }
    }

    private void boom(Vec3 where, float power, boolean breakBlocks, boolean setFire) {
        if (detonated) return;
        detonated = true;
        if (!(level() instanceof ServerLevel sl)) return;

        float p = Math.max(power, 2.0F); // tiny explosions often show tiny/no FX

        Level.ExplosionInteraction interaction = breakBlocks
                ? Level.ExplosionInteraction.TNT
                : Level.ExplosionInteraction.NONE;

        // Real explosion (entity damage/knockback, but optionally no block damage)
        try {
            sl.explode(this, where.x, where.y, where.z, p, setFire, interaction);
        } catch (NoSuchMethodError ignored) {
            // Fallback for mappings that only expose the 5-arg overload
            sl.explode(this, where.x, where.y, where.z, p, interaction);
        }

        // Force visible FX when we chose to NOT break blocks
        if (interaction == Level.ExplosionInteraction.NONE) {
            ensureExplosionFx(sl, where, p);
        }
    }

    private static void ensureExplosionFx(ServerLevel sl, Vec3 where, float power) {
        // Big boom sound
        sl.playSound(null, where.x, where.y, where.z,
                SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4.0F, 1.0F);

        // One large column (“emitter”) + several puffs scaled by power
        sl.sendParticles(ParticleTypes.EXPLOSION_EMITTER, where.x, where.y, where.z,
                1, 0.0, 0.0, 0.0, 0.0);

        int puffs = 6 + Mth.floor(power * 3.5F);
        sl.sendParticles(ParticleTypes.EXPLOSION, where.x, where.y, where.z,
                puffs, power * 0.4, power * 0.4, power * 0.4, 0.1);
    }

    private void strikeLightning(Vec3 where) {
        if (struck) return; // optional: avoid double-strikes
        struck = true;

        if (level() instanceof ServerLevel sl) {
            LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(sl);

            if (bolt != null) {
                bolt.moveTo(where.x, where.y, where.z);
                bolt.setDamage(10);

                // Attribute the lightning to the thrower (for advancements/aggro)
                if (getOwner() instanceof ServerPlayer sp) {
                    bolt.setCause(sp);
                }

                sl.addFreshEntity(bolt);
            }
        }
    }

    /** Ensure it drops/picks up YOUR item (not vanilla trident). */
    @Override
    protected ItemStack getPickupItem() {
        return EvermightItems.THE_SHOVEL_ITEM.get().getDefaultInstance();
    }
}
