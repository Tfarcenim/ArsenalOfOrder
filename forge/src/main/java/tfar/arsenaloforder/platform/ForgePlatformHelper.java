package tfar.arsenaloforder.platform;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.PacketDistributor;
import org.apache.commons.lang3.tuple.Pair;
import tfar.arsenaloforder.PacketHandlerForge;
import tfar.arsenaloforder.network.C2SModPacket;
import tfar.arsenaloforder.network.S2CModPacket;
import tfar.arsenaloforder.platform.services.IPlatformHelper;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    public static final List<Pair<String, Supplier<? extends Item>>> ITEMS = new ArrayList<>();
    public static final List<Pair<String,Supplier<? extends Block>>> BLOCKS = new ArrayList<>();
    public static final List<Pair<String,Supplier<CreativeModeTab>>> TABS = new ArrayList<>();


    @Override
    public <I extends Item> Supplier<I> itemSupplier(String id, Supplier<I> supplier) {
        ITEMS.add(Pair.of(id,supplier));
        return supplier;
    }

    @Override
    public Supplier<CreativeModeTab> tabSupplier(String id, Supplier<CreativeModeTab> supplier) {
        TABS.add(Pair.of(id,supplier));
        return supplier;
    }

    int i;
    @Override
    public <MSG extends S2CModPacket> void registerClientPlayPacket(Class<MSG> packetLocation, Function<FriendlyByteBuf, MSG> reader) {
        PacketHandlerForge.INSTANCE.registerMessage(i++, packetLocation, MSG::write, reader, PacketHandlerForge.wrapS2C());
    }

    @Override
    public <MSG extends C2SModPacket> void registerServerPlayPacket(Class<MSG>  packetLocation, Function<FriendlyByteBuf, MSG> reader) {
        PacketHandlerForge.INSTANCE.registerMessage(i++, packetLocation, MSG::write, reader, PacketHandlerForge.wrapC2S());
    }

    @Override
    public void sendToClient(S2CModPacket msg, ServerPlayer player) {
        PacketHandlerForge.sendToClient(msg,player);
    }

    @Override
    public void sendToServer(C2SModPacket msg) {
        PacketHandlerForge.sendToServer(msg);
    }

    @Override
    public void sendToTracking(S2CModPacket msg, Entity entity, boolean includeSelf) {
        PacketDistributor<Entity> trackingEntity = includeSelf ? PacketDistributor.TRACKING_ENTITY_AND_SELF : PacketDistributor.TRACKING_ENTITY;
        PacketHandlerForge.INSTANCE.send(trackingEntity.with(() -> entity),msg);
    }

    @Override
    public boolean onExplosionStart(Level level, Explosion explosion) {
        return ForgeEventFactory.onExplosionStart(level,explosion);
    }

    @Override
    public boolean getMobGriefingEvent(Level level, Entity source) {
        return net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(level, source);
    }

    @Override
    public void onExplosionDetonate(Level level, Explosion explosion, List<Entity> list, double diameter) {
        net.minecraftforge.event.ForgeEventFactory.onExplosionDetonate(level, explosion,list,diameter);
    }

}