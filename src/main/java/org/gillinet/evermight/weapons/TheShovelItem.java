package org.gillinet.evermight.weapons;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.Level;

public class TheShovelItem extends TridentItem {
    public TheShovelItem(Item.Properties props) {
        super(props.stacksTo(1).durability(500));
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity user, int timeLeft) {
        if (!(user instanceof Player player)) return;

        int used = this.getUseDuration(stack, user) - timeLeft;
        if (used < 10) return;

        if (!level.isClientSide) {
            TheShovel thrown = new TheShovel(level, player, stack.copy());
            thrown.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.0F);
            level.addFreshEntity(thrown);

            if (!player.getAbilities().instabuild) {
                InteractionHand hand = player.getUsedItemHand();
                EquipmentSlot slot = LivingEntity.getSlotForHand(hand);   // map hand -> slot

                // ✅ put it here (replaces the old broadcastBreakEvent/consumer)
                stack.hurtAndBreak(1, player, slot);

                // vanilla trident behavior: remove while in flight
                player.getInventory().removeItem(stack);
            }

            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    net.minecraft.sounds.SoundEvents.TRIDENT_THROW,
                    net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.0F);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
    }
}