package tfar.arsenaloforder.item;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import tfar.arsenaloforder.ArsenalOfOrder;

import java.util.List;

public class VoltaicClawsItem extends SwordItem implements ChargeOnKill{
    public VoltaicClawsItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean b = super.hurtEnemy(stack, target, attacker);
        if (b) {
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,200));
            target.addEffect(new MobEffectInstance(MobEffects.WITHER,200));
        }
        return b;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
        appendText(stack,tooltipComponents);
    }


    @Override
    public void activate(ServerPlayer player) {

        EntityHitResult entityHitResult = ArsenalOfOrder.pickEntity(player,5,5,0);
        if (entityHitResult != null) {
            Entity entity = entityHitResult.getEntity();
            LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(player.level());
            if (lightningbolt != null) {
                lightningbolt.moveTo(entity.position());
                lightningbolt.setCause(player);
                player.level().addFreshEntity(lightningbolt);
                SoundEvent soundevent = SoundEvents.TRIDENT_THUNDER;
                float f1 = 5.0F;
                player.level().playSound(null,entity.blockPosition(),soundevent, SoundSource.PLAYERS, f1, 1.0F);
            }
        }
    }
}
