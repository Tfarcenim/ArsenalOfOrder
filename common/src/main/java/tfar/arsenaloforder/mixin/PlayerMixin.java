package tfar.arsenaloforder.mixin;

import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import tfar.arsenaloforder.PlayerDuck;

@Mixin(Player.class)
public class PlayerMixin implements PlayerDuck {
    @Unique
    int beamTimer;

    @Override
    public void setBeamTimer(int beamTimer) {
        this.beamTimer = beamTimer;
    }

    @Override
    public int beamTimer() {
        return beamTimer;
    }
}
