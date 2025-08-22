package wootrevived.woot.recipes.fluid_infuser;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wootrevived.woot.registries.RecipesRegistry;
import wootrevived.woot.util.recipes.WootContainer;
import wootrevived.woot.util.recipes.WootRecipe;

import java.util.ArrayList;
import java.util.List;

public class FluidInfuserRecipe extends WootRecipe {
    public FluidInfuserRecipe(ResourceLocation recipeId, int energy, @Nullable List<Ingredient> inputItems, @Nullable List<FluidStack> inputFluids, @Nullable ItemStack outputItem, @Nullable FluidStack outputFluid) {
        super(recipeId, energy, inputItems, inputFluids, outputItem, outputFluid);
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipesRegistry.FLUID_INFUSER_RECIPE_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return RecipesRegistry.FLUID_INFUSER_RECIPE_TYPE.get();
    }

    public FluidStack getInputFluid(){
        return this.inputFluids.get(0);
    }

    public Ingredient getInputIngredient(){
        return this.inputItems.get(0);
    }

    public FluidStack getOutputFluid(){
        return this.outputFluid;
    }

    @Override
    public boolean matches(@NotNull Container container, @NotNull Level level) {
        if(container instanceof WootContainer wootContainer){
            if(!getInputFluid().isFluidEqual(wootContainer.getFluid(0)))
                return false;

            return getInputIngredient().test(container.getItem(1));
        }

        return false;
    }

    public static void loadRecipes(@NotNull RecipeManager manager){
        Validator.clear();
        for(Recipe<?> recipe : manager.getRecipes()) {
            if(recipe instanceof FluidInfuserRecipe fluidInfuserRecipe) {
                Validator.add(fluidInfuserRecipe.getInputItems(), fluidInfuserRecipe.getInputFluids());
            }
        }
    }

    public static class Validator {
        private static final List<Ingredient> validIngredients = new ArrayList<>();
        private static final List<FluidStack> validFluids = new ArrayList<>();

        public static boolean isCatalystValid(ItemStack item){
            for(Ingredient ingredient : validIngredients){
                if(ingredient.test(item))
                    return true;
            }
            return false;
        }

        public static boolean isFluidValid(FluidStack fluid){
            for(FluidStack fluidStack : validFluids){
                if(fluidStack.isFluidEqual(fluid))
                    return true;
            }
            return false;
        }

        protected static void add(List<Ingredient> items, List<FluidStack> fluids){
            validIngredients.add(items.get(0));
            validFluids.add(fluids.get(0));
        }

        protected static void clear(){
            validIngredients.clear();
            validFluids.clear();
        }
    }
}
