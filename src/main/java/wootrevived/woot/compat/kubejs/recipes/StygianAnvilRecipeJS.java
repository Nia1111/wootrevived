package wootrevived.woot.compat.kubejs.recipes;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ComponentRole;
import dev.latvian.mods.kubejs.recipe.component.IngredientComponent;
import dev.latvian.mods.kubejs.recipe.component.ItemStackComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public interface StygianAnvilRecipeJS {
    RecipeKey<ItemStack> OUTPUT = ItemStackComponent.ITEM_STACK.key("output", ComponentRole.OUTPUT);
    RecipeKey<Ingredient> BASE = IngredientComponent.INGREDIENT.key("base", ComponentRole.INPUT);
    RecipeKey<Ingredient> FIRST_COMPLEMENTARY = IngredientComponent.INGREDIENT.key("first_complementary", ComponentRole.INPUT).defaultOptional();
    RecipeKey<Ingredient> SECOND_COMPLEMENTARY = IngredientComponent.INGREDIENT.key("second_complementary", ComponentRole.INPUT).defaultOptional();
    RecipeKey<Ingredient> THIRD_COMPLEMENTARY = IngredientComponent.INGREDIENT.key("third_complementary", ComponentRole.INPUT).defaultOptional();
    RecipeKey<Ingredient> FOURTH_COMPLEMENTARY = IngredientComponent.INGREDIENT.key("fourth_complementary", ComponentRole.INPUT).defaultOptional();

    RecipeSchema SCHEMA = new RecipeSchema(OUTPUT, BASE, FIRST_COMPLEMENTARY, SECOND_COMPLEMENTARY, THIRD_COMPLEMENTARY, FOURTH_COMPLEMENTARY);
}
