package tfar.arsenaloforder.item;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChampionSaberItem extends SwordItem implements ChargeOnKill {
    public ChampionSaberItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
        appendText(stack,tooltipComponents);
    }


    @Override
    public void activate(ServerPlayer player) {
        FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(player.level(), player.getX(), player.getY(), player.getZ(), ItemStack.EMPTY);
        player.level().addFreshEntity(fireworkRocketEntity);

        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();

        float f2 = 5 * 2.0F;
        double k1 = x - f2;
        double l1 = x + f2;
        double i2 = y - f2;
        double i1 = y + f2;
        double j2 = z - f2;
        double j1 = z + f2;
        List<Entity> targets = player.level().getEntities(player, new AABB(k1, i2, j2, l1, i1, j1));

        for (Entity entity:targets) {
            if (!entity.ignoreExplosion()) {

            }
        }

    }
}
