package wootrevived.woot.compat.kubejs.recipes;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;

public interface ItemInfuserRecipeJS {
    RecipeKey<ItemStack> OUTPUT = ItemStackComponent.ITEM_STACK.key("output", ComponentRole.OUTPUT);
    RecipeKey<Integer> ENERGY = NumberComponent.INT.key("energy", ComponentRole.OTHER);
    RecipeKey<FluidStack> FLUID = FluidStackComponent.FLUID_STACK.key("fluid", ComponentRole.INPUT);
    RecipeKey<Ingredient> INGREDIENT = IngredientComponent.INGREDIENT.key("ingredient", ComponentRole.INPUT);
    RecipeKey<Ingredient> AUGMENT = IngredientComponent.INGREDIENT.key("augment", ComponentRole.INPUT).defaultOptional();

    RecipeSchema SCHEMA = new RecipeSchema(OUTPUT, ENERGY, FLUID, INGREDIENT, AUGMENT);
}
