package tfar.arsenaloforder.item;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import tfar.arsenaloforder.ArsenalOfOrder;
import tfar.arsenaloforder.NBTHelper;

import java.util.List;

public interface ChargeOnKill {
    String CHARGE = ArsenalOfOrder.id("charge").toString();

    default void addCharge(ItemStack stack) {
        int charge = getCharge(stack);
        charge++;
        NBTHelper.setInt(stack,CHARGE,charge);
    }

    default void resetCharge(ItemStack stack) {
        NBTHelper.setInt(stack,CHARGE,0);
    }

    default void appendText(ItemStack stack, List<Component> componentList) {
        int charge = getCharge(stack);
        componentList.add(Component.literal("Charge: "+charge));
    }

    default int getCharge(ItemStack stack) {
        return NBTHelper.getIntOrDefault(stack, CHARGE,0);
    }

    default void activateAbility(ServerPlayer player,ItemStack stack) {
        int charge = getCharge(stack);
        if (charge>=100) {
            activate(player);
            resetCharge(stack);
        }
    }

    void activate(ServerPlayer player);
}
