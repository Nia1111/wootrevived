package wootrevived.woot.recipes.fluid_infuser;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import wootrevived.woot.Woot;
import wootrevived.woot.registries.BlocksRegistry;
import wootrevived.woot.registries.RecipesRegistry;
import wootrevived.woot.util.recipes.WootFinishedRecipe;

import java.util.List;
import java.util.function.Consumer;

public class FluidInfuserRecipeBuilder {
    private FluidStack inputFluid;
    private Ingredient ingredient;
    private final FluidStack outputFluid;
    private int energy;

    protected FluidInfuserRecipeBuilder(FluidStack outputFluid) {
        this.outputFluid = outputFluid;
    }

    public static FluidInfuserRecipeBuilder fluidInfuserRecipe(Fluid outputFluid, int amount) {
        return new FluidInfuserRecipeBuilder(new FluidStack(outputFluid, amount));
    }

    public static FluidInfuserRecipeBuilder fluidInfuserRecipe(Fluid outputFluid){
        return new FluidInfuserRecipeBuilder(new FluidStack(outputFluid, 1000));
    }

    public static FluidInfuserRecipeBuilder fluidInfuserRecipe(FluidStack outputFluid){
        return new FluidInfuserRecipeBuilder(outputFluid);
    }

    public FluidInfuserRecipeBuilder fluid(Fluid fluid, int amount){
        this.inputFluid = new FluidStack(fluid, amount);
        return this;
    }

    public FluidInfuserRecipeBuilder fluid(Fluid fluid){
        this.inputFluid = new FluidStack(fluid, 1000);
        return this;
    }

    public FluidInfuserRecipeBuilder fluid(FluidStack fluidStack){
        this.inputFluid = fluidStack;
        return this;
    }

    public FluidInfuserRecipeBuilder ingredient(Ingredient ingredient){
        this.ingredient = ingredient;
        return this;
    }

    public FluidInfuserRecipeBuilder energy(int energy){
        this.energy = energy;
        return this;
    }

    public void save(Consumer<FinishedRecipe> consumer, String path){
        consumer.accept(new Result(
                ResourceLocation.tryBuild(Woot.MOD_ID, BlocksRegistry.FLUID_INFUSER_TAG + "/" + path),
                energy, inputFluid, ingredient, outputFluid
        ));
    }

    public static class Result extends WootFinishedRecipe {
        protected Result(ResourceLocation recipeId, int energy, FluidStack inputFluid, Ingredient ingredient, FluidStack outputFluid) {
            super(recipeId, energy, List.of(ingredient), List.of(inputFluid), null, outputFluid);
        }

        @Override
        public @NotNull RecipeSerializer<?> getType() {
            return RecipesRegistry.FLUID_INFUSER_RECIPE_SERIALIZER.get();
        }
    }
}
