package tfar.arsenaloforder.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.Vec3;
import tfar.arsenaloforder.ExplosionUtils;
import tfar.arsenaloforder.client.ArsenalOfOrderClient;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public record CustomExplosionPacketS2C(ExplosionUtils.ExplosionType<?> type
, double x, double y, double z, float power, List<BlockPos> toBlow, @Nullable Vec3 knockback) implements S2CModPacket {

    public static CustomExplosionPacketS2C fromPacket(FriendlyByteBuf buffer) {
        ExplosionUtils.ExplosionType<?> type = ExplosionUtils.ExplosionType.REVERSE_MAP.get(buffer.readUtf());
        double x = buffer.readDouble();
        double y = buffer.readDouble();
        double z = buffer.readDouble();
        float power = buffer.readFloat();
        int i = Mth.floor(x);
        int j = Mth.floor(y);
        int k = Mth.floor(z);
        List<BlockPos> toBlow = buffer.readList((p_178850_) -> {
            int l = p_178850_.readByte() + i;
            int i1 = p_178850_.readByte() + j;
            int j1 = p_178850_.readByte() + k;
            return new BlockPos(l, i1, j1);
        });
        Optional<Vec3> vec3 = buffer.readOptional(buf -> new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble()));
        return new CustomExplosionPacketS2C(type,x,y,z,power,toBlow,vec3.orElse(null));
    }

    @Override
    public void handleClient() {
        ArsenalOfOrderClient.handle(this);
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeUtf(ExplosionUtils.ExplosionType.MAP.get(type));
        buffer.writeDouble(this.x);
        buffer.writeDouble(this.y);
        buffer.writeDouble(this.z);
        buffer.writeFloat(this.power);
        int i = Mth.floor(this.x);
        int j = Mth.floor(this.y);
        int k = Mth.floor(this.z);
        buffer.writeCollection(this.toBlow, (p_178855_, p_178856_) -> {
            int l = p_178856_.getX() - i;
            int i1 = p_178856_.getY() - j;
            int j1 = p_178856_.getZ() - k;
            p_178855_.writeByte(l);
            p_178855_.writeByte(i1);
            p_178855_.writeByte(j1);
        });
        buffer.writeOptional(Optional.ofNullable(knockback),(buf, vec3) -> {
            buf.writeDouble(vec3.x);
            buf.writeDouble(vec3.y);
            buf.writeDouble(vec3.z);
        });

    }
}
