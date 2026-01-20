package tfar.arsenaloforder.platform.services;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import tfar.arsenaloforder.network.C2SModPacket;
import tfar.arsenaloforder.network.S2CModPacket;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public interface IPlatformHelper {

    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Gets the name of the environment type as a string.
     *
     * @return The name of the environment type.
     */
    default String getEnvironmentName() {

        return isDevelopmentEnvironment() ? "development" : "production";
    }

    <I extends Item> Supplier<I> itemSupplier(String id, Supplier<I> supplier);
    Supplier<CreativeModeTab> tabSupplier(String id, Supplier<CreativeModeTab> supplier);

    <MSG extends S2CModPacket> void registerClientPlayPacket(Class<MSG> packetLocation, Function<FriendlyByteBuf,MSG> reader);
    <MSG extends C2SModPacket> void registerServerPlayPacket(Class<MSG> packetLocation, Function<FriendlyByteBuf,MSG> reader);

    void sendToClient(S2CModPacket msg, ServerPlayer player);
    void sendToServer(C2SModPacket msg);
    void sendToTracking(S2CModPacket msg, Entity entity, boolean includeSelf);

    boolean onExplosionStart(Level level, Explosion explosion);
    boolean getMobGriefingEvent(Level level, Entity source);
    void onExplosionDetonate(Level level, Explosion explosion, List<Entity> list, double diameter);
}