package wootrevived.woot.recipes.fluid_infuser;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wootrevived.woot.registries.RecipesRegistry;
import wootrevived.woot.util.recipes.WootRecipeInput;
import wootrevived.woot.util.recipes.WootRecipe;

import java.util.ArrayList;
import java.util.List;

public class FluidInfuserRecipe extends WootRecipe {
    public static final MapCodec<FluidInfuserRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Codec.INT.fieldOf("energy").forGetter(FluidInfuserRecipe::getEnergy),
            Ingredient.CODEC.listOf().fieldOf("inputIngredients").forGetter(FluidInfuserRecipe::getInputItems),
            FluidStack.CODEC.listOf().fieldOf("inputFluids").forGetter(FluidInfuserRecipe::getInputFluids),
            FluidStack.CODEC.fieldOf("outputFluid").forGetter(FluidInfuserRecipe::getOutputFluid)
    ).apply(inst, FluidInfuserRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, FluidInfuserRecipe> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, FluidInfuserRecipe::getEnergy,
            Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.collection(NonNullList::createWithCapacity)), FluidInfuserRecipe::getInputItems,
            FluidStack.STREAM_CODEC.apply(ByteBufCodecs.collection(NonNullList::createWithCapacity)), FluidInfuserRecipe::getInputFluids,
            FluidStack.STREAM_CODEC, FluidInfuserRecipe::getOutputFluid,
            FluidInfuserRecipe::new
    );

    public FluidInfuserRecipe(int energy, @Nullable List<Ingredient> inputItems, @Nullable List<FluidStack> inputFluids, @Nullable FluidStack outputFluid) {
        super(energy, inputItems, inputFluids, null, outputFluid);
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
    public boolean matches(@NotNull WootRecipeInput input, @NotNull Level level) {
        if(!FluidStack.isSameFluidSameComponents(getInputFluid(), input.getFluid(0)))
            return false;

        return getInputIngredient().test(input.getItem(1));
    }

    public static void loadRecipes(@NotNull RecipeManager manager){
        Validator.clear();
        for(RecipeHolder<FluidInfuserRecipe> recipeHolder : manager.getAllRecipesFor(RecipesRegistry.FLUID_INFUSER_RECIPE_TYPE.get())) {
            Validator.add(recipeHolder.value().getInputItems(), recipeHolder.value().getInputFluids());
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
                if(FluidStack.isSameFluidSameComponents(fluidStack, fluid))
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
