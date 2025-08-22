package wootrevived.woot.datagen;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import wootrevived.woot.datagen.recipes.*;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider {
    public Recipes(PackOutput packOutput) { super(packOutput); }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer){
        Vanilla.registerRecipes(this, consumer);
        StygianAnvil.registerRecipes(this, consumer);
        DyeLiquifier.registerRecipes(this, consumer);
        FluidInfuser.registerRecipes(this, consumer);
        ItemInfuser.registerRecipes(this, consumer);
    }

    public InventoryChangeTrigger.TriggerInstance hasItem(ItemLike item) { return has(item); }

    public InventoryChangeTrigger.TriggerInstance hasItem(TagKey<Item> item) { return has(item); }
}
