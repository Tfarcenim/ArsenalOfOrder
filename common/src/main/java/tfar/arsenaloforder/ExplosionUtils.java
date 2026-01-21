package tfar.arsenaloforder;

import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import tfar.arsenaloforder.network.CustomExplosionPacketS2C;
import tfar.arsenaloforder.platform.Services;

import java.util.HashMap;
import java.util.Map;

public class ExplosionUtils {

    @FunctionalInterface
    public interface ExplosionType<E extends Explosion> {
        Map<ExplosionType<?>,String> MAP = new HashMap<>();
        Map<String,ExplosionType<?>> REVERSE_MAP = new HashMap<>();
        ExplosionType<LightExplosion> LIGHT_EXPLOSION = put(LightExplosion::new,"light");

        static <E extends  Explosion> ExplosionType<E>put(ExplosionType<E>type,String s) {
            MAP.put(type,s);
            REVERSE_MAP.put(s,type);
            return type;
        }

        E create(Level level, @Nullable Entity source, @Nullable DamageSource damageSource, @Nullable ExplosionDamageCalculator damageCalculator, double x, double y, double z, float pRadius, boolean pFire, Explosion.BlockInteraction blockInteraction);
    }

    public static Explosion explode(Level level, @Nullable Entity pSource, double x, double y, double z, float pRadius, Level.ExplosionInteraction pExplosionInteraction, ExplosionType type) {
        return explode(level,pSource, null, null, x, y, z, pRadius, false, pExplosionInteraction,type );
    }

    public static Explosion explode(Level level, @Nullable Entity pSource, @Nullable DamageSource pDamageSource, @Nullable ExplosionDamageCalculator pDamageCalculator, double x, double y, double z, float pRadius, boolean pFire, Level.ExplosionInteraction pExplosionInteraction, ExplosionType type) {
        return explode(level,pSource, pDamageSource, pDamageCalculator, x, y, z, pRadius, pFire, pExplosionInteraction, true,type);
    }

    public static Explosion explode(Level level, @Nullable Entity pSource, @Nullable DamageSource pDamageSource, @Nullable ExplosionDamageCalculator pDamageCalculator, double x, double y, double z, float pRadius, boolean pFire, Level.ExplosionInteraction pExplosionInteraction, boolean pSpawnParticles, ExplosionType type) {

        Explosion.BlockInteraction explosion$blockinteraction = switch (pExplosionInteraction) {
            case NONE -> Explosion.BlockInteraction.KEEP;
            case BLOCK -> getDestroyType(level, GameRules.RULE_BLOCK_EXPLOSION_DROP_DECAY);
            case MOB ->
                    Services.PLATFORM.getMobGriefingEvent(level, pSource) ? getDestroyType(level,GameRules.RULE_MOB_EXPLOSION_DROP_DECAY) : Explosion.BlockInteraction.KEEP;
            case TNT -> getDestroyType(level,GameRules.RULE_TNT_EXPLOSION_DROP_DECAY);
        };
        Explosion explosion = type.create(level, pSource, pDamageSource, pDamageCalculator, x, y, z, pRadius, pFire, explosion$blockinteraction);
        if (Services.PLATFORM.onExplosionStart(level, explosion)) return explosion;
        explosion.explode();
        explosion.finalizeExplosion(pSpawnParticles);

        if (!explosion.interactsWithBlocks()) {
            explosion.clearToBlow();
        }

        for(ServerPlayer serverplayer : ((ServerLevel)level).players()) {
            if (serverplayer.distanceToSqr(x, y, z) < 4096.0D) {
                //serverplayer.connection.send(new ClientboundExplodePacket(x, y, z, pRadius, explosion.getToBlow(), explosion.getHitPlayers().get(serverplayer)));
                Services.PLATFORM.sendToClient(new CustomExplosionPacketS2C(type,
                        x, y, z, pRadius, explosion.getToBlow(), explosion.getHitPlayers().get(serverplayer)),serverplayer);
            }
        }

        return explosion;
    }

    public static Explosion.BlockInteraction getDestroyType(Level level, GameRules.Key<GameRules.BooleanValue> rule) {
        return level.getGameRules().getBoolean(rule) ? Explosion.BlockInteraction.DESTROY_WITH_DECAY : Explosion.BlockInteraction.DESTROY;
    }
}
