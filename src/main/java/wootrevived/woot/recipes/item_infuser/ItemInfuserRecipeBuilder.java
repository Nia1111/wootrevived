package wootrevived.woot.recipes.item_infuser;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import wootrevived.woot.Woot;
import wootrevived.woot.registries.BlocksRegistry;
import wootrevived.woot.registries.RecipesRegistry;
import wootrevived.woot.util.recipes.WootFinishedRecipe;

import java.util.List;
import java.util.function.Consumer;

public class ItemInfuserRecipeBuilder {
    private FluidStack fluid;
    private Ingredient ingredient;
    private Ingredient augment;
    private final ItemStack output;
    private int energy;

    protected ItemInfuserRecipeBuilder(ItemLike output, int count) {
        this.output = output.asItem().getDefaultInstance();
        this.output.setCount(count);
        this.augment = Ingredient.EMPTY;
    }

    public static ItemInfuserRecipeBuilder itemInfuserRecipe(ItemLike output, int count) {
        return new ItemInfuserRecipeBuilder(output, count);
    }

    public static ItemInfuserRecipeBuilder itemInfuserRecipe(ItemLike output){
        return new ItemInfuserRecipeBuilder(output, 1);
    }

    public ItemInfuserRecipeBuilder fluid(Fluid fluid, int amount){
        this.fluid = new FluidStack(fluid, amount);
        return this;
    }

    public ItemInfuserRecipeBuilder fluid(Fluid fluid){
        this.fluid = new FluidStack(fluid, 1000);
        return this;
    }

    public ItemInfuserRecipeBuilder fluid(FluidStack fluidStack){
        this.fluid = fluidStack;
        return this;
    }

    public ItemInfuserRecipeBuilder ingredient(Ingredient ingredient){
        this.ingredient = ingredient;
        return this;
    }

    public ItemInfuserRecipeBuilder augment(Ingredient augment){
        this.augment = augment;
        return this;
    }

    public ItemInfuserRecipeBuilder energy(int energy){
        this.energy = energy;
        return this;
    }

    public void save(Consumer<FinishedRecipe> consumer){
        save(consumer, ForgeRegistries.ITEMS.getKey(output.getItem()).getPath());
    }

    public void save(Consumer<FinishedRecipe> consumer, String path){
        consumer.accept(new Result(
                ResourceLocation.tryBuild(Woot.MOD_ID, BlocksRegistry.ITEM_INFUSER_TAG + "/" + path),
                energy, fluid, ingredient, augment, output
        ));
    }

    public static class Result extends WootFinishedRecipe {
        protected Result(ResourceLocation recipeId, int energy, FluidStack fluid, Ingredient ingredient, Ingredient augment, ItemStack output) {
            super(recipeId, energy, List.of(ingredient, augment), List.of(fluid), output, null);
        }

        @Override
        public @NotNull RecipeSerializer<?> getType() {
            return RecipesRegistry.ITEM_INFUSER_RECIPE_SERIALIZER.get();
        }
    }
}
