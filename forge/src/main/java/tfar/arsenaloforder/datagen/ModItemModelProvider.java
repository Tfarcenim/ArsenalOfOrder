package tfar.arsenaloforder.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import tfar.arsenaloforder.ArsenalOfOrder;
import tfar.arsenaloforder.Constants;
import tfar.arsenaloforder.init.ModItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        generatedItem(ModItems.CHAMPION_SABER.get());
        generatedItem(ModItems.MALEVOLENT_WARSCYTHE.get());
        generatedItem(ModItems.INCOMPLETE_BLADE.get());
        generatedItem(ModItems.WINDBURST.get());

        generatedItem(ModItems.CHAMPION_RUNE.get());
        generatedItem(ModItems.MALEVOLENT_RUNE.get());
        generatedItem(ModItems.VOLTAIC_RUNE.get());
        generatedItem(ModItems.WINDBURST_RUNE.get());
    }

    private void generatedItem(Item item) {
        String path = BuiltInRegistries.ITEM.getKey(item).getPath();
        generatedItem(path);
    }

    private void generatedItem(String path) {
        singleTexture(path, new ResourceLocation("item/handheld"),
                "layer0", ArsenalOfOrder.id("item/" + path));
    }

    private void handheldItem(Item item) {
        String path = BuiltInRegistries.ITEM.getKey(item).getPath();
        handheldItem(path);
    }

    private void handheldItem(String path) {
        singleTexture(path, new ResourceLocation("item/handheld"),
                "layer0", ArsenalOfOrder.id("item/" + path));
    }
}
