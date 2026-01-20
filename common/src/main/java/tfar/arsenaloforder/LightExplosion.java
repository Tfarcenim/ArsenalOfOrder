package tfar.arsenaloforder;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LightExplosion extends Explosion {
    public LightExplosion(Level level, @Nullable Entity source, double toBlowX, double toBlowY, double toBlowZ, float radius, List<BlockPos> positions) {
        super(level, source, toBlowX, toBlowY, toBlowZ, radius, positions);
    }

    public LightExplosion(Level level, @Nullable Entity source, double toBlowX, double toBlowY, double toBlowZ, float radius, boolean fire, BlockInteraction blockInteraction, List<BlockPos> positions) {
        super(level, source, toBlowX, toBlowY, toBlowZ, radius, fire, blockInteraction, positions);
    }

    public LightExplosion(Level level, @Nullable Entity source, double toBlowX, double toBlowY, double toBlowZ, float radius, boolean fire, BlockInteraction blockInteraction) {
        super(level, source, toBlowX, toBlowY, toBlowZ, radius, fire, blockInteraction);
    }

    public LightExplosion(Level level, @Nullable Entity source, @Nullable DamageSource damageSource, @Nullable ExplosionDamageCalculator damageCalculator, double toBlowX, double toBlowY, double toBlowZ, float radius, boolean fire, BlockInteraction blockInteraction) {
        super(level, source, damageSource, damageCalculator, toBlowX, toBlowY, toBlowZ, radius, fire, blockInteraction);
    }
}
