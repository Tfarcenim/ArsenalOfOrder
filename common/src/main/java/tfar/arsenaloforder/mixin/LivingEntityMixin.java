package tfar.arsenaloforder.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import tfar.arsenaloforder.item.WindburstItem;
import tfar.arsenaloforder.network.EntityEventPacketS2C;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow public abstract ItemStack getUseItem();

    @Redirect(method = "hurt",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;broadcastEntityEvent(Lnet/minecraft/world/entity/Entity;B)V"))
    private void changeSound(Level instance, Entity entity, byte state) {
        ItemStack stack = getUseItem();
        if (stack.getItem() instanceof WindburstItem windburstItem) {
            EntityEventPacketS2C.broadcast(entity, EntityEventPacketS2C.Event.PARRY);
        }else {
            instance.broadcastEntityEvent(entity, state);
        }
    }
    @Inject(method = "isBlocking",at = @At(value = "RETURN",ordinal = 1),cancellable = true,locals = LocalCapture.CAPTURE_FAILHARD)
    private void generousTimeframe(CallbackInfoReturnable<Boolean> cir, Item item) {
        if (!cir.getReturnValue()) {
            if (item instanceof WindburstItem) {
                cir.setReturnValue(true);
            }
        }
    }
}
