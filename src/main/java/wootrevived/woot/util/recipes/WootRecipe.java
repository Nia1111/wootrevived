package wootrevived.woot.util.recipes;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class WootRecipe implements Recipe<Container> {
    protected final ResourceLocation recipeId;

    protected final ArrayList<Ingredient> inputItems = new ArrayList<>();
    protected final ArrayList<FluidStack> inputFluids = new ArrayList<>();

    protected ItemStack outputItem = null;
    protected FluidStack outputFluid = null;

    protected final int energy;

    protected WootRecipe(ResourceLocation recipeId, int energy, @Nullable List<Ingredient> inputItems, @Nullable List<FluidStack> inputFluids, @Nullable ItemStack outputItem, @Nullable FluidStack outputFluid){
        this.recipeId = recipeId;
        this.energy = energy;

        if(inputItems != null) this.inputItems.addAll(inputItems);
        if(inputFluids != null) this.inputFluids.addAll(inputFluids);
        if(outputItem != null) this.outputItem = outputItem;
        if(outputFluid != null) this.outputFluid = outputFluid;

        this.inputItems.trimToSize();
        this.inputFluids.trimToSize();
    }

    public List<Ingredient> getInputItems() {
        return inputItems;
    }

    public List<FluidStack> getInputFluids() {
        return inputFluids;
    }

    public ItemStack getOutputItem() {
        return outputItem.copy();
    }

    public FluidStack getOutputFluid() {
        return outputFluid.copy();
    }

    public int getEnergy() {
        return energy;
    }

    // Recipe
    @Override
    public @NotNull ItemStack assemble(@NotNull Container container, @NotNull RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public abstract @NotNull RecipeSerializer<?> getSerializer();

    @Override
    public abstract @NotNull RecipeType<?> getType();
}
