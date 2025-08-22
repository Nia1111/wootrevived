package wootrevived.woot.recipes.item_infuser;

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

public class ItemInfuserRecipe extends WootRecipe {
    public ItemInfuserRecipe(ResourceLocation recipeId, int energy, @Nullable List<Ingredient> inputItems, @Nullable List<FluidStack> inputFluids, @Nullable ItemStack outputItem, @Nullable FluidStack outputFluid) {
        super(recipeId, energy, inputItems, inputFluids, outputItem, outputFluid);
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipesRegistry.ITEM_INFUSER_RECIPE_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return RecipesRegistry.ITEM_INFUSER_RECIPE_TYPE.get();
    }

    public FluidStack getInputFluid(){
        return this.inputFluids.get(0);
    }

    public Ingredient getInputIngredient(){
        return this.inputItems.get(0);
    }

    public Ingredient getAugmentIngredient(){
        return this.inputItems.get(1);
    }

    @Override
    public boolean matches(@NotNull Container container, @NotNull Level level) {
        if(container instanceof WootContainer wootContainer){
            if(!getInputFluid().isFluidEqual(wootContainer.getFluid(0)))
                return false;

            if(!getInputIngredient().test(container.getItem(1)))
                return false;

            return getAugmentIngredient().isEmpty() || getAugmentIngredient().test(container.getItem(2));
        }

        return false;
    }

    public static void loadRecipes(@NotNull RecipeManager manager){
        Validator.clear();
        for(Recipe<?> recipe : manager.getRecipes()) {
            if(recipe instanceof ItemInfuserRecipe fluidInfuserRecipe) {
                Validator.add(fluidInfuserRecipe.getInputItems(), fluidInfuserRecipe.getInputFluids());
            }
        }
    }

    public static class Validator {
        private static final List<Ingredient> validIngredients = new ArrayList<>();
        private static final List<Ingredient> validAugments = new ArrayList<>();
        private static final List<FluidStack> validFluids = new ArrayList<>();

        public static boolean isIngredientValid(ItemStack item){
            for(Ingredient ingredient : validIngredients){
                if(ingredient.test(item))
                    return true;
            }
            return false;
        }

        public static boolean isAugmentValid(ItemStack item){
            for(Ingredient ingredient : validAugments){
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
            validAugments.add(items.get(1));
            validFluids.add(fluids.get(0));
        }

        protected static void clear(){
            validIngredients.clear();
            validAugments.clear();
            validFluids.clear();
        }
    }
}
