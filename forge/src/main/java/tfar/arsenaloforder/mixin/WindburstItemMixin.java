package tfar.arsenaloforder.mixin;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import tfar.arsenaloforder.item.WindburstItem;

@Mixin(WindburstItem.class)
public class WindburstItemMixin extends Item {
    public WindburstItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return net.minecraftforge.common.ToolActions.DEFAULT_SHIELD_ACTIONS.contains(toolAction);
    }
}
