package tfar.arsenaloforder.item;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import tfar.arsenaloforder.PlayerDuck;
import tfar.arsenaloforder.network.EntityEventPacketS2C;

import java.util.List;

public class MalevolentWarscytheItem extends HoeItem implements ChargeOnKill {

    public static final int TIMER = 20;

    public MalevolentWarscytheItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
        appendText(stack,tooltipComponents);
    }


    @Override
    public void activate(ServerPlayer player) {
        ((PlayerDuck)player).setBeamTimer(TIMER);
        EntityEventPacketS2C.broadcast(player, EntityEventPacketS2C.Event.BEAM);
    }
}
