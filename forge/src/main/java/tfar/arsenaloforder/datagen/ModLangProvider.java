package tfar.arsenaloforder.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import org.codehaus.plexus.util.StringUtils;
import tfar.arsenaloforder.Constants;

import java.util.function.Supplier;

public class ModLangProvider extends LanguageProvider {
    public ModLangProvider(PackOutput output) {
        super(output, Constants.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        BuiltInRegistries.ITEM.stream().filter(item -> BuiltInRegistries.ITEM.getKey(item).getNamespace().equals(Constants.MOD_ID))
                .forEach(item -> addDefaultItem(() -> item));
    }

    protected void addDefaultItem(Supplier<? extends Item> supplier) {
        addItem(supplier,getNameFromItem(supplier.get()));
    }

    public static String getNameFromItem(Item item) {
        return StringUtils.capitaliseAllWords(item.getDescriptionId().split("\\.")[2].replace("_", " "));
    }

}
