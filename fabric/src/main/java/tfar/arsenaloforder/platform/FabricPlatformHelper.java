package tfar.arsenaloforder.platform;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import tfar.arsenaloforder.network.C2SModPacket;
import tfar.arsenaloforder.network.S2CModPacket;
import tfar.arsenaloforder.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public <I extends Item> Supplier<I> itemSupplier(String id, Supplier<I> supplier) {
        return null;
    }

    @Override
    public Supplier<CreativeModeTab> tabSupplier(String id, Supplier<CreativeModeTab> supplier) {
        return null;
    }

    @Override
    public <MSG extends S2CModPacket> void registerClientPlayPacket(Class<MSG> packetLocation, Function<FriendlyByteBuf, MSG> reader) {

    }

    @Override
    public <MSG extends C2SModPacket> void registerServerPlayPacket(Class<MSG> packetLocation, Function<FriendlyByteBuf, MSG> reader) {

    }

    @Override
    public void sendToClient(S2CModPacket msg, ServerPlayer player) {

    }

    @Override
    public void sendToServer(C2SModPacket msg) {

    }

    @Override
    public void sendToTracking(S2CModPacket msg, Entity entity, boolean includeSelf) {

    }

    @Override
    public boolean onExplosionStart(Level level, Explosion explosion) {
        return false;
    }

    @Override
    public boolean getMobGriefingEvent(Level level, Entity source) {
        return false;
    }

    @Override
    public void onExplosionDetonate(Level level, Explosion explosion, List<Entity> list, double diameter) {

    }
}
