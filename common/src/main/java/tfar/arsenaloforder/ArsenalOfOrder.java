package tfar.arsenaloforder;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import tfar.arsenaloforder.init.ModItems;
import tfar.arsenaloforder.network.PacketHandler;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
public class ArsenalOfOrder {

    // The loader specific projects are able to import and use any code from the common project. This allows you to
    // write the majority of your code here and load it from your loader specific projects. This example has some
    // code that gets invoked by the entry point of the loader specific projects.
    public static void init() {

        // It is common for all supported loaders to provide a similar feature that can not be used directly in the
        // common code. A popular way to get around this is using Java's built-in service loader feature to create
        // your own abstraction layer. You can learn more about this in our provided services class. In this example
        // we have an interface in the common code and use a loader specific implementation to delegate our call to
        // the platform specific approach.
        PacketHandler.registerPackets();
        ModItems.init();
    }

    public static ResourceLocation id(String s) {
        return new ResourceLocation(Constants.MOD_ID,s);
    }

    @Nullable
    public static EntityHitResult pickEntity(Entity pEntity, double pBlockInteractionRange, double pEntityInteractionRange, float pPartialTick) {
        double d0 = Math.max(pBlockInteractionRange, pEntityInteractionRange);
        double d1 = Mth.square(d0);
        Vec3 vec3 = pEntity.getEyePosition(pPartialTick);
        HitResult hitresult = pEntity.pick(d0, pPartialTick, false);
        double d2 = hitresult.getLocation().distanceToSqr(vec3);
        if (hitresult.getType() != HitResult.Type.MISS) {
            d1 = d2;
            d0 = Math.sqrt(d2);
        }

        Vec3 vec31 = pEntity.getViewVector(pPartialTick);
        Vec3 vec32 = vec3.add(vec31.x * d0, vec31.y * d0, vec31.z * d0);
        float f = 1.0F;
        AABB aabb = pEntity.getBoundingBox().expandTowards(vec31.scale(d0)).inflate(f, f, f);
        EntityHitResult entityhitresult = ProjectileUtil.getEntityHitResult(
                pEntity, vec3, vec32, aabb, entity -> !entity.isSpectator() && entity.isPickable(), d1
        );
        return entityhitresult;
    }

    public static void tick(Player player) {
        ((PlayerDuck)player).arsenalOfOrder$tick();
    }

}
//WindBurst:  A breezerod katana that does 18dmg has an attack speed 1.4  and has the ability to Parry Back any attack when right clicked at the right timing
// . when the bar gets full press a keybind to slash forward and deal tons of knockback upwards
//
//Voltaic Claws:  Wolverine type claws with  that deals 14 dmg with an attack speed of 2 that applies slowness and bleeding dmg.
// When Bar is full press keybind to throw lighting forward  dealing 30 dmg  (adding the trident lighting sfx would be cool)
//
//Malevolent Warscythe : A wither bone scythe that deals 20 attack dmg and an attack speed of 1.2 that applies wither apon hit.
//when bar gets full (requires 100 kills) after pressing a keybind start a chant using beacon sfx that cuts through terrian 30 blocks forward (basicly world cutting slash)
//
//Champions Saber A Steel saber with a redstone hilt that does 16 dmg and an attack speed of 1.6
//apon hitting an entity applies 3 true dmg shortly afterwards. when Bar is filled press a keybind to do an Aoe explosion of light that deals 20 true dmg
//
//How to obtain each rune
//WindBurst 5% chance drop from blazes
//Voltaic Claws 100% chance drop from charged creeper
//Malevolent Warscythe 25% chance drop from a wither
//Champions Saber 50% chance drop from a warden