package tfar.arsenaloforder.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import tfar.arsenaloforder.ArsenalOfOrder;
import tfar.arsenaloforder.init.ModItems;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHER_STAR), Ingredient.of(Items.NETHERITE_SWORD),
                Ingredient.EMPTY, RecipeCategory.COMBAT,ModItems.INCOMPLETE_BLADE.get())
                .unlocks("has_nether_star",has(Items.NETHER_STAR))
                .save(consumer, ArsenalOfOrder.id("incomplete_blade"));

        SmithingTransformRecipeBuilder.smithing(Ingredient.of(ModItems.CHAMPION_RUNE.get()), Ingredient.of(ModItems.INCOMPLETE_BLADE.get()),
                        Ingredient.EMPTY, RecipeCategory.COMBAT,ModItems.CHAMPION_SABER.get())
                .unlocks("has_incomplete_blade",has(ModItems.INCOMPLETE_BLADE.get()))
                .save(consumer, ArsenalOfOrder.id("champion_saber"));

        SmithingTransformRecipeBuilder.smithing(Ingredient.of(ModItems.MALEVOLENT_RUNE.get()), Ingredient.of(ModItems.INCOMPLETE_BLADE.get()),
                        Ingredient.EMPTY, RecipeCategory.COMBAT,ModItems.MALEVOLENT_WARSCYTHE.get())
                .unlocks("has_incomplete_blade",has(ModItems.INCOMPLETE_BLADE.get()))
                .save(consumer, ArsenalOfOrder.id("malevolent_warscythe"));

        SmithingTransformRecipeBuilder.smithing(Ingredient.of(ModItems.VOLTAIC_RUNE.get()), Ingredient.of(ModItems.INCOMPLETE_BLADE.get()),
                        Ingredient.EMPTY, RecipeCategory.COMBAT,ModItems.VOLTAIC_CLAWS.get())
                .unlocks("has_incomplete_blade",has(ModItems.INCOMPLETE_BLADE.get()))
                .save(consumer, ArsenalOfOrder.id("voltaic_claws"));

        SmithingTransformRecipeBuilder.smithing(Ingredient.of(ModItems.WINDBURST_RUNE.get()), Ingredient.of(ModItems.INCOMPLETE_BLADE.get()),
                        Ingredient.EMPTY, RecipeCategory.COMBAT,ModItems.WINDBURST.get())
                .unlocks("has_incomplete_blade",has(ModItems.INCOMPLETE_BLADE.get()))
                .save(consumer, ArsenalOfOrder.id("windburst"));
    }
}
