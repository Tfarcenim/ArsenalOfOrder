package tfar.arsenaloforder.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import tfar.arsenaloforder.item.ChargeOnKill;
import tfar.arsenaloforder.platform.Services;

public enum KeybindPacketC2S implements C2SModPacket {
    USE_ABILITY;

    public static KeybindPacketC2S fromPacket(FriendlyByteBuf buf) {
        return buf.readEnum(KeybindPacketC2S.class);
    }

    public void send() {
        Services.PLATFORM.sendToServer(this);
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeEnum(this);
    }

    @Override
    public void handleServer(ServerPlayer player) {
        switch (this) {
            case USE_ABILITY -> {
                ItemStack stack = player.getMainHandItem();
                if (stack.getItem() instanceof ChargeOnKill chargeOnKill) {
                    chargeOnKill.activateAbility(player,stack);
                }
            }
        }
    }
}