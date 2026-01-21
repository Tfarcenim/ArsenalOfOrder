package tfar.arsenaloforder.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import tfar.arsenaloforder.ArsenalOfOrder;
import tfar.arsenaloforder.platform.Services;

import java.util.Locale;

public class PacketHandler {

    public static void registerPackets() {
        Services.PLATFORM.registerServerPlayPacket(KeybindPacketC2S.class, KeybindPacketC2S::fromPacket);
        Services.PLATFORM.registerClientPlayPacket(EntityEventPacketS2C.class, EntityEventPacketS2C::fromPacket);
        Services.PLATFORM.registerClientPlayPacket(CustomExplosionPacketS2C.class, CustomExplosionPacketS2C::fromPacket);
    }

    public static void sendToServer(C2SModPacket packet) {
        Services.PLATFORM.sendToServer(packet);
    }

    public static void sendTo(S2CModPacket packet, ServerPlayer player) {
            Services.PLATFORM.sendToClient(packet, player);
    }

    public static void sendPacketToAll(MinecraftServer server,S2CModPacket packet)
    {
        for (ServerPlayer player : server.getPlayerList().getPlayers())
        {
            sendTo(packet, player);
        }
    }


    public static ResourceLocation packet(Class<?> clazz) {
        return ArsenalOfOrder.id(clazz.getName().toLowerCase(Locale.ROOT));
    }


}
