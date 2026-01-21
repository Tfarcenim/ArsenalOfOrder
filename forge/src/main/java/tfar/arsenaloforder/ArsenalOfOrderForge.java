package tfar.arsenaloforder;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.RegisterEvent;
import tfar.arsenaloforder.client.ArsenalOfOrderClientForge;
import tfar.arsenaloforder.init.ModItems;
import tfar.arsenaloforder.item.ChargeOnKill;
import tfar.arsenaloforder.platform.ForgePlatformHelper;

import java.util.function.Supplier;

@Mod(Constants.MOD_ID)
public class ArsenalOfOrderForge {
    
    public ArsenalOfOrderForge() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(ModDatagen::gather);
        bus.addListener(this::register);
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.

        // Use Forge to bootstrap the Common mod.
      //  ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER,CollapseCycleConfig.Server.SPEC);

        if (FMLEnvironment.dist.isClient()) {
            ArsenalOfOrderClientForge.init(bus);
        }
        MinecraftForge.EVENT_BUS.addListener(this::onKill);
        MinecraftForge.EVENT_BUS.addListener(this::drops);
        MinecraftForge.EVENT_BUS.addListener(this::hurt);
        MinecraftForge.EVENT_BUS.addListener(this::tick);
        ArsenalOfOrder.init();
    }

    void tick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            ArsenalOfOrder.tick(event.player);
        }
    }

    void hurt(LivingHurtEvent event) {
        DamageSource source = event.getSource();
        if (source.getEntity() instanceof LightningBolt lightningBolt && source.is(DamageTypes.LIGHTNING_BOLT)) {
            LivingEntity living = lightningBolt.getCause();
            if (living != null && living.getMainHandItem().is(ModItems.VOLTAIC_CLAWS.get())) {
                event.setAmount(30);
            }
        }
    }
    void drops(LivingDropsEvent event) {
        LivingEntity target = event.getEntity();
        Item item = Items.AIR;
        if (target instanceof Blaze && target.getRandom().nextDouble() < .05) {
            item = ModItems.WINDBURST_RUNE.get();
        }

        if (target instanceof Creeper creeper&& creeper.isPowered()) {
            item = ModItems.VOLTAIC_RUNE.get();
        }

        if (target instanceof WitherBoss && target.getRandom().nextDouble() < .25) {
            item = ModItems.MALEVOLENT_RUNE.get();
        }

        if (target instanceof Warden && target.getRandom().nextDouble() < .5) {
            item = ModItems.CHAMPION_RUNE.get();
        }
        if (item != Items.AIR) {
            event.getDrops().add(new ItemEntity(target.level(), target.getX(), target.getY(), target.getZ(), item.getDefaultInstance()));
        }
    }
    //How to obtain each rune
//WindBurst 5% chance drop from blazes
//Voltaic Claws 100% chance drop from charged creeper
//Malevolent Warscythe 25% chance drop from a wither
//Champions Saber 50% chance drop from a warden
    void onKill(LivingDeathEvent event) {
        DamageSource source = event.getSource();
        if (source.getEntity() instanceof LivingEntity living) {
            ItemStack handStack = living.getMainHandItem();
            if (handStack.getItem() instanceof ChargeOnKill chargeOnKill) {
                chargeOnKill.addCharge(handStack);
            }
        }
    }

    void register(RegisterEvent event) {
        if ((Registry<?>)event.getVanillaRegistry() == BuiltInRegistries.ITEM) {
            ForgePlatformHelper.ITEMS.forEach(stringSupplierPair -> event.register(Registries.ITEM,ArsenalOfOrder.id(stringSupplierPair.getKey()),
                    (Supplier<Item>)stringSupplierPair.getValue()));
        } else if ((Registry<?>)event.getVanillaRegistry() == BuiltInRegistries.CREATIVE_MODE_TAB) {
            ForgePlatformHelper.TABS.forEach(stringSupplierPair -> event.register(Registries.CREATIVE_MODE_TAB,ArsenalOfOrder.id(stringSupplierPair.getKey()),
                    stringSupplierPair.getValue()));
        }
    }
}