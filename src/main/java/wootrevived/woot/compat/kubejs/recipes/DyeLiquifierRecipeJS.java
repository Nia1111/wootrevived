package wootrevived.woot.compat.kubejs.recipes;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ComponentRole;
import dev.latvian.mods.kubejs.recipe.component.IngredientComponent;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.crafting.Ingredient;

public interface DyeLiquifierRecipeJS {
    RecipeKey<Ingredient> INGREDIENT = IngredientComponent.INGREDIENT.key("ingredient", ComponentRole.INPUT);
    RecipeKey<Integer> ENERGY = NumberComponent.INT.key("energy", ComponentRole.OTHER);
    RecipeKey<Float> RED_MULTIPLIER = NumberComponent.FLOAT.key("red_multiplier", ComponentRole.OTHER);
    RecipeKey<Float> YELLOW_MULTIPLIER = NumberComponent.FLOAT.key("yellow_multiplier", ComponentRole.OTHER);
    RecipeKey<Float> BLUE_MULTIPLIER = NumberComponent.FLOAT.key("blue_multiplier", ComponentRole.OTHER);
    RecipeKey<Float> WHITE_MULTIPLIER = NumberComponent.FLOAT.key("white_multiplier", ComponentRole.OTHER);

    RecipeSchema SCHEMA = new RecipeSchema(INGREDIENT, ENERGY, RED_MULTIPLIER, YELLOW_MULTIPLIER, BLUE_MULTIPLIER, WHITE_MULTIPLIER);
}
