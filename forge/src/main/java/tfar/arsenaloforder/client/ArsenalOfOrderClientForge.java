package tfar.arsenaloforder.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import tfar.arsenaloforder.PlayerDuck;
import tfar.arsenaloforder.network.KeybindPacketC2S;

public class ArsenalOfOrderClientForge {

    public static void init(IEventBus bus) {
        bus.addListener(ArsenalOfOrderClientForge::keybinds);
        MinecraftForge.EVENT_BUS.addListener(ArsenalOfOrderClientForge::tick);
        MinecraftForge.EVENT_BUS.addListener(ArsenalOfOrderClientForge::renderAfter);
    }

    static void renderAfter(RenderPlayerEvent.Post event) {
        Player player = event.getEntity();
        if (PlayerDuck.of(player).beamTimer() > 0) {
            renderBeaconBeam(event.getPoseStack(), event.getMultiBufferSource(), (AbstractClientPlayer) player, event.getPartialTick());
        }
    }

    public static void renderBeaconBeam(PoseStack poseStack, MultiBufferSource buffer, AbstractClientPlayer entity, float partialTicks) {
        Level level = Minecraft.getInstance().level;
        Vec3 entityPos = entity.getPosition(partialTicks);
        Vec3 beamStart = entityPos;
        double d0 = beamStart.x();
        double d1 = beamStart.y();
        double d2 = beamStart.z();
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        Vec3 beamEnd = entityPos.add(entity.getLookAngle().scale(16));

        poseStack.pushPose();
        poseStack.translate(beamEnd.x - d0, beamEnd.y - d1, beamEnd.z - d2);
        poseStack.translate(0,1,0);

        Vec3 vec32 = beamStart.subtract(beamEnd).normalize();
        float f5 = (float) Math.acos(vec32.y);
        float f6 = (float) Math.atan2(vec32.z, vec32.x);
        poseStack.mulPose(Axis.YP.rotationDegrees(((float) (Math.PI / 2) - f6) * (180.0F / (float) Math.PI)));
        poseStack.mulPose(Axis.XP.rotationDegrees(f5 * (180.0F / (float) Math.PI)));

        long i = level.getGameTime();
        float width = 1;
        BeaconRenderer.renderBeaconBeam(poseStack, multibuffersource$buffersource,
                BeaconRenderer.BEAM_LOCATION, partialTicks, 1, i, 0, (int)beamEnd.distanceTo(beamStart), new float[]{1, 1f, 0}, .2f * width, .25f * width);
        poseStack.popPose();
    }

        static void keybinds(RegisterKeyMappingsEvent event) {
        event.register(ArsenalOfOrderClient.ABILITY);
    }

    static void tick(TickEvent.ClientTickEvent event) {
        if (event.phase== TickEvent.Phase.END) {
            while (ArsenalOfOrderClient.ABILITY.consumeClick()) {
                KeybindPacketC2S.USE_ABILITY.send();
            }
        }
    }
}
