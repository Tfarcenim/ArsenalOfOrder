package tfar.arsenaloforder;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import tfar.arsenaloforder.datagen.ModItemModelProvider;
import tfar.arsenaloforder.datagen.ModLangProvider;
import tfar.arsenaloforder.datagen.ModRecipeProvider;

import java.util.concurrent.CompletableFuture;

public class ModDatagen {
    public static void gather(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        generator.addProvider(true,new ModItemModelProvider(packOutput,existingFileHelper));
        generator.addProvider(true,new ModRecipeProvider(packOutput));
        generator.addProvider(true,new ModLangProvider(packOutput));
    }
}
