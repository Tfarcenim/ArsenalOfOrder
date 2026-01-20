package tfar.arsenaloforder;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public interface PlayerDuck {

    static PlayerDuck of(Player player) {
        return (PlayerDuck) player;
    }
    void setBeamTimer(int beamTimer);

    int beamTimer();

    default void arsenalOfOrder$tick() {
        int beamTimer = beamTimer();
        Player player = (Player) this;
        if (beamTimer>0) {
            if (!player.level().isClientSide) {
                HitResult pick = player.pick(32, 0, false);
                if (pick.getType() != HitResult.Type.MISS) {
                    BlockHitResult blockHitResult = (BlockHitResult) pick;
                    player.level().destroyBlock(blockHitResult.getBlockPos(),true,player);
                }
            }
            beamTimer--;
            setBeamTimer(beamTimer);
        }
    }
}
