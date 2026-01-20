package tfar.arsenaloforder.init;

import com.google.common.base.Suppliers;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import tfar.arsenaloforder.item.ChampionSaberItem;
import tfar.arsenaloforder.item.MalevolentWarscytheItem;
import tfar.arsenaloforder.item.VoltaicClawsItem;
import tfar.arsenaloforder.item.WindburstItem;
import tfar.arsenaloforder.platform.Services;

import java.util.function.Supplier;

public class ModItems {
    public static final Supplier<Item> INCOMPLETE_BLADE = Services.PLATFORM.itemSupplier("incomplete_blade", Suppliers.memoize(() ->
            new Item(new Item.Properties())));

    public static final Supplier<Item> CHAMPION_SABER = Services.PLATFORM.itemSupplier("champion_saber", Suppliers.memoize(() ->
            new ChampionSaberItem(Tiers.NETHERITE,11,-2.4f,new Item.Properties())));

    public static final Supplier<Item> MALEVOLENT_WARSCYTHE = Services.PLATFORM.itemSupplier("malevolent_warscythe", Suppliers.memoize(() ->
            new MalevolentWarscytheItem(Tiers.NETHERITE,15,-2.8f,new Item.Properties())));

    public static final Supplier<Item> VOLTAIC_CLAWS = Services.PLATFORM.itemSupplier("voltaic_claws", Suppliers.memoize(() ->
            new VoltaicClawsItem(Tiers.NETHERITE,9,-2,new Item.Properties())));

    public static final Supplier<Item> WINDBURST = Services.PLATFORM.itemSupplier("windburst", Suppliers.memoize(() ->
            new WindburstItem(Tiers.NETHERITE,13,-2.6f,new Item.Properties())));

    public static final Supplier<Item> CHAMPION_RUNE = Services.PLATFORM.itemSupplier("champion_rune", Suppliers.memoize(() ->
            new Item(new Item.Properties())));

    public static final Supplier<Item> MALEVOLENT_RUNE = Services.PLATFORM.itemSupplier("malevolent_rune", Suppliers.memoize(() ->
            new Item(new Item.Properties())));

    public static final Supplier<Item> VOLTAIC_RUNE = Services.PLATFORM.itemSupplier("voltaic_rune", Suppliers.memoize(() ->
            new Item(new Item.Properties())));

    public static final Supplier<Item> WINDBURST_RUNE = Services.PLATFORM.itemSupplier("windburst_rune", Suppliers.memoize(() ->
            new Item(new Item.Properties())));

    public static final Supplier<CreativeModeTab> TAB = Services.PLATFORM.tabSupplier("items",
            () -> CreativeModeTab.builder(null,-1).title(Component.translatable("itemGroup.arsenaloforder"))
                    .icon(() -> INCOMPLETE_BLADE.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(INCOMPLETE_BLADE.get());

                        output.accept(CHAMPION_SABER.get());
                        output.accept(MALEVOLENT_WARSCYTHE.get());
                        output.accept(VOLTAIC_CLAWS.get());
                        output.accept(WINDBURST.get());

                        output.accept(CHAMPION_RUNE.get());
                        output.accept(MALEVOLENT_RUNE.get());
                        output.accept(VOLTAIC_RUNE.get());
                        output.accept(WINDBURST_RUNE.get());
                    })
                    .build());

    public static void init() {

    }
}
