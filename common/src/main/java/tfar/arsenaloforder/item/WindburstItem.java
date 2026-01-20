package tfar.arsenaloforder.item;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import tfar.arsenaloforder.ArsenalOfOrder;

import java.util.List;

public class WindburstItem extends SwordItem implements ChargeOnKill {
    public WindburstItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 4;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
        appendText(stack,tooltipComponents);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void activate(ServerPlayer player) {
        player.addDeltaMovement(player.getLookAngle());
        player.hurtMarked = true;
        EntityHitResult entityHitResult = ArsenalOfOrder.pickEntity(player,5,5,0);
        if (entityHitResult != null) {
            Entity entity = entityHitResult.getEntity();
            if (entity instanceof LivingEntity livingEntity){
                double d0 = entity.getX() - player.getX();

                double d1= entity.getZ() - player.getZ();
                knockback(livingEntity,1,-d0,-d1);
            }
        }
    }
    public void knockback(LivingEntity living,double strength, double x, double z) {
        strength *= 1.0D - living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
        if (strength > 0.0D) {
            living.hasImpulse = true;
            Vec3 vec3 = living.getDeltaMovement();
            Vec3 vec31 = (new Vec3(x, 0.0D, z)).normalize().scale(strength);
            living.setDeltaMovement(vec3.x / 2.0D - vec31.x, living.onGround() ? vec3.y / 2.0D + strength : vec3.y, vec3.z / 2.0D - vec31.z);
        }
    }


    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        if (livingEntity instanceof Player player) {
            player.getCooldowns().addCooldown(stack.getItem(),20);
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof Player player) {
            player.getCooldowns().addCooldown(stack.getItem(),20);
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BLOCK;
    }
}
