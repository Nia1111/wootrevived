package wootrevived.woot.compat.kubejs.recipes;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;

public interface ItemInfuserRecipeJS {
    RecipeKey<List<FluidStack>> INPUT_FLUID = FluidStackComponent.FLUID_STACK.asList().key("inputFluids", ComponentRole.INPUT);
    RecipeKey<List<Ingredient>> INPUT_ITEM = IngredientComponent.INGREDIENT.asList().key("inputIngredients", ComponentRole.INPUT);
    RecipeKey<ItemStack> OUTPUT_ITEM = ItemStackComponent.ITEM_STACK.key("outputItem", ComponentRole.OUTPUT);
    RecipeKey<Integer> ENERGY = NumberComponent.INT.key("energy", ComponentRole.OTHER);

    RecipeSchema SCHEMA = new RecipeSchema(INPUT_FLUID, INPUT_ITEM, OUTPUT_ITEM, ENERGY);
}
