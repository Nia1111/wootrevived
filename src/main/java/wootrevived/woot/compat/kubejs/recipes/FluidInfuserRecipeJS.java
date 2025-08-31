package wootrevived.woot.compat.kubejs.recipes;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ComponentRole;
import dev.latvian.mods.kubejs.recipe.component.FluidStackComponent;
import dev.latvian.mods.kubejs.recipe.component.IngredientComponent;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;

public interface FluidInfuserRecipeJS {
    RecipeKey<FluidStack> OUTPUT_FLUID = FluidStackComponent.FLUID_STACK.key("output_fluid", ComponentRole.OUTPUT);
    RecipeKey<Integer> ENERGY = NumberComponent.INT.key("energy", ComponentRole.OTHER);
    RecipeKey<FluidStack> INPUT_FLUID = FluidStackComponent.FLUID_STACK.key("input_fluid", ComponentRole.INPUT);
    RecipeKey<Ingredient> INGREDIENT = IngredientComponent.INGREDIENT.key("ingredient", ComponentRole.INPUT);

    RecipeSchema SCHEMA = new RecipeSchema(OUTPUT_FLUID, ENERGY, INPUT_FLUID, INGREDIENT);
}
