package wootrevived.woot.compat.kubejs.recipes;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ComponentRole;
import dev.latvian.mods.kubejs.recipe.component.IngredientComponent;
import dev.latvian.mods.kubejs.recipe.component.ItemStackComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public interface StygianAnvilRecipeJS {
    RecipeKey<List<Ingredient>> INPUT_ITEM = IngredientComponent.INGREDIENT.asList().key("inputIngredients", ComponentRole.INPUT);
    RecipeKey<ItemStack> OUTPUT_ITEM = ItemStackComponent.ITEM_STACK.key("outputItem", ComponentRole.OUTPUT);

    RecipeSchema SCHEMA = new RecipeSchema(INPUT_ITEM, OUTPUT_ITEM);
}
