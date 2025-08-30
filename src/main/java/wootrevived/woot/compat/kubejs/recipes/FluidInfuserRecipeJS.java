package wootrevived.woot.compat.kubejs.recipes;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ComponentRole;
import dev.latvian.mods.kubejs.recipe.component.FluidStackComponent;
import dev.latvian.mods.kubejs.recipe.component.IngredientComponent;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;

public interface FluidInfuserRecipeJS {
    RecipeKey<List<FluidStack>> INPUT_FLUID = FluidStackComponent.FLUID_STACK.asList().key("inputFluids", ComponentRole.INPUT);
    RecipeKey<List<Ingredient>> INPUT_ITEM = IngredientComponent.INGREDIENT.asList().key("inputIngredients", ComponentRole.INPUT);
    RecipeKey<FluidStack> OUTPUT_FLUID = FluidStackComponent.FLUID_STACK.key("outputFluid", ComponentRole.OUTPUT);
    RecipeKey<Integer> ENERGY = NumberComponent.INT.key("energy", ComponentRole.OTHER);

    RecipeSchema SCHEMA = new RecipeSchema(INPUT_FLUID, INPUT_ITEM, OUTPUT_FLUID, ENERGY);
}
