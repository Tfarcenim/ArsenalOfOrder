package tfar.arsenaloforder.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public abstract void setRemainingFireTicks(int remainingFireTicks);

    @Shadow private int remainingFireTicks;

    @Shadow public abstract void setSecondsOnFire(int seconds);

    @Shadow public abstract DamageSources damageSources();

    @Shadow public abstract boolean hurt(DamageSource source, float amount);

    /**
     * @author
     * @reason
     */
    @Overwrite
    public void thunderHit(ServerLevel level, LightningBolt lightning) {
        this.setRemainingFireTicks(this.remainingFireTicks + 1);
        if (this.remainingFireTicks == 0) {
            this.setSecondsOnFire(8);
        }

        this.hurt(damageSources().source(DamageTypes.LIGHTNING_BOLT,lightning),5);
    }
}
