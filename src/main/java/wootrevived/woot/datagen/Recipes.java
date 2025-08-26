package wootrevived.woot.datagen;

import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import wootrevived.woot.datagen.recipes.*;

public class Recipes extends RecipeProvider {
    public Recipes(PackOutput packOutput) { super(packOutput); }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        Vanilla.registerRecipes(this, output);
        StygianAnvil.registerRecipes(this, output);
        DyeLiquifier.registerRecipes(this, output);
        FluidInfuser.registerRecipes(this, output);
        ItemInfuser.registerRecipes(this, output);
    }

    public Criterion<InventoryChangeTrigger.TriggerInstance> hasItem(ItemLike item) { return has(item); }

    public Criterion<InventoryChangeTrigger.TriggerInstance> hasItem(TagKey<Item> item) { return has(item); }
}
