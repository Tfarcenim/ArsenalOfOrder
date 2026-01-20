package tfar.arsenaloforder.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import tfar.arsenaloforder.client.ArsenalOfOrderClient;
import tfar.arsenaloforder.platform.Services;

public record EntityEventPacketS2C(int entityId,Event event) implements S2CModPacket{

    public static EntityEventPacketS2C fromPacket(FriendlyByteBuf buf) {
        return new EntityEventPacketS2C(buf.readInt(),buf.readEnum(Event.class));
    }

    public enum Event{
        PARRY,BEAM;
    }

    @Override
    public void handleClient() {
        ArsenalOfOrderClient.handle(this);
    }

    public static void broadcast(Entity entity,Event event) {
        Services.PLATFORM.sendToTracking(new EntityEventPacketS2C(entity.getId(),event),entity,true);
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeEnum(event);
    }
}
