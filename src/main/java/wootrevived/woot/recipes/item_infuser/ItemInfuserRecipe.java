package wootrevived.woot.recipes.item_infuser;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
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

public class ItemInfuserRecipe extends WootRecipe {
    public static final MapCodec<ItemInfuserRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("energy").forGetter(ItemInfuserRecipe::getEnergy),
            Ingredient.CODEC.listOf().fieldOf("inputIngredients").forGetter(ItemInfuserRecipe::getInputItems),
            FluidStack.CODEC.listOf().fieldOf("inputFluids").forGetter(ItemInfuserRecipe::getInputFluids),
            ItemStack.CODEC.fieldOf("outputItem").forGetter(ItemInfuserRecipe::getOutputItem)
    ).apply(inst, ItemInfuserRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ItemInfuserRecipe> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, ItemInfuserRecipe::getEnergy,
            Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.collection(NonNullList::createWithCapacity)), ItemInfuserRecipe::getInputItems,
            FluidStack.STREAM_CODEC.apply(ByteBufCodecs.collection(NonNullList::createWithCapacity)), ItemInfuserRecipe::getInputFluids,
            ItemStack.STREAM_CODEC, ItemInfuserRecipe::getOutputItem,
            ItemInfuserRecipe::new
    );

    public ItemInfuserRecipe(int energy, @Nullable List<Ingredient> inputItems, @Nullable List<FluidStack> inputFluids, @Nullable ItemStack outputItem) {
        super(energy, inputItems, inputFluids, outputItem, null);
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
    public boolean matches(@NotNull WootRecipeInput input, @NotNull Level level) {
        if(!FluidStack.isSameFluidSameComponents(getInputFluid(), input.getFluid(0)))
            return false;

        if(!getInputIngredient().test(input.getItem(1)))
            return false;

        return getAugmentIngredient().isEmpty() || getAugmentIngredient().test(input.getItem(2));
    }

    public static void loadRecipes(@NotNull RecipeManager manager){
        Validator.clear();
        for(RecipeHolder<ItemInfuserRecipe> recipeHolder : manager.getAllRecipesFor(RecipesRegistry.ITEM_INFUSER_RECIPE_TYPE.get())) {
            Validator.add(recipeHolder.value().getInputItems(), recipeHolder.value().getInputFluids());
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
                if(FluidStack.isSameFluidSameComponents(fluidStack, fluid))
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
