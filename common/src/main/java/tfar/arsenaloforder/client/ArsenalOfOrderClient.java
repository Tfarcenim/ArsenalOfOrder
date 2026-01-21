package tfar.arsenaloforder.client;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import org.lwjgl.glfw.GLFW;
import tfar.arsenaloforder.PlayerDuck;
import tfar.arsenaloforder.item.MalevolentWarscytheItem;
import tfar.arsenaloforder.network.CustomExplosionPacketS2C;
import tfar.arsenaloforder.network.EntityEventPacketS2C;

public class ArsenalOfOrderClient {

    public static final KeyMapping ABILITY = new KeyMapping("Use Ability", GLFW.GLFW_KEY_O,"Arsenal Of Order");

    public static void handle(EntityEventPacketS2C entityEventPacketS2C) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null) {
            Entity entity = level.getEntity(entityEventPacketS2C.entityId());
            if (entity != null) {
                switch (entityEventPacketS2C.event()) {
                    case PARRY -> {
                        entity.playSound(SoundEvents.EVOKER_FANGS_ATTACK, 1.0F, 0.8F + entity.level().random.nextFloat() * 0.4F);
                    }
                    case BEAM -> {
                        if (entity instanceof PlayerDuck playerDuck) {
                            playerDuck.setBeamTimer(MalevolentWarscytheItem.TIMER);
                        }
                    }
                }
            }
        }
    }

    public static void handle(CustomExplosionPacketS2C packet) {
        ClientLevel level = Minecraft.getInstance().level;
        LocalPlayer player = Minecraft.getInstance().player;
        Explosion explosion = packet.type().create(level,null, null,null,
                packet.x(), packet.y(), packet.z(), packet.power(),false, Explosion.BlockInteraction.KEEP);
        explosion.getToBlow().addAll( packet.toBlow());
        explosion.finalizeExplosion(true);
        if (packet.knockback() != null) {
            player.setDeltaMovement(player.getDeltaMovement().add(packet.knockback()));
        }
    }
}
