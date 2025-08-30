package wootrevived.woot.recipes.dye_liquifier;

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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wootrevived.woot.registries.RecipesRegistry;
import wootrevived.woot.util.common.DyeMakeup;
import wootrevived.woot.util.recipes.WootRecipe;
import wootrevived.woot.util.recipes.WootRecipeInput;

import java.util.ArrayList;
import java.util.List;

public class DyeLiquifierRecipe extends WootRecipe {
    public static final MapCodec<DyeLiquifierRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Codec.INT.fieldOf("energy").forGetter(DyeLiquifierRecipe::getEnergy),
            Codec.FLOAT.fieldOf("red").forGetter(DyeLiquifierRecipe::getInternalRed),
            Codec.FLOAT.fieldOf("yellow").forGetter(DyeLiquifierRecipe::getInternalYellow),
            Codec.FLOAT.fieldOf("blue").forGetter(DyeLiquifierRecipe::getInternalBlue),
            Codec.FLOAT.fieldOf("white").forGetter(DyeLiquifierRecipe::getInternalWhite),
            Ingredient.CODEC.listOf().fieldOf("inputIngredients").forGetter(DyeLiquifierRecipe::getInputItems)
    ).apply(inst, DyeLiquifierRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, DyeLiquifierRecipe> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, DyeLiquifierRecipe::getEnergy,
            ByteBufCodecs.FLOAT, DyeLiquifierRecipe::getInternalRed,
            ByteBufCodecs.FLOAT, DyeLiquifierRecipe::getInternalYellow,
            ByteBufCodecs.FLOAT, DyeLiquifierRecipe::getInternalBlue,
            ByteBufCodecs.FLOAT, DyeLiquifierRecipe::getInternalWhite,
            Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.collection(NonNullList::createWithCapacity)), DyeLiquifierRecipe::getInputItems,
            DyeLiquifierRecipe::new
    );

    private final float red;
    private final float yellow;
    private final float blue;
    private final float white;

    public DyeLiquifierRecipe(int energy, float red, float yellow, float blue, float white, @Nullable List<Ingredient> inputItems) {
        super(energy, inputItems, null, null, null);
        this.red = red;
        this.yellow = yellow;
        this.blue = blue;
        this.white = white;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipesRegistry.DYE_LIQUIFIER_RECIPE_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return RecipesRegistry.DYE_LIQUIFIER_RECIPE_TYPE.get();
    }

    public int getRed() {
        return Math.round(red * DyeMakeup.LCM);
    }

    public int getYellow() {
        return Math.round(yellow * DyeMakeup.LCM);
    }

    public int getBlue() {
        return Math.round(blue * DyeMakeup.LCM);
    }

    public int getWhite() {
        return Math.round(white * DyeMakeup.LCM);
    }

    public float getInternalRed() {
        return red;
    }

    public float getInternalYellow() {
        return yellow;
    }

    public float getInternalBlue() {
        return blue;
    }

    public float getInternalWhite() {
        return white;
    }

    @Override
    public boolean matches(WootRecipeInput input, Level level) {
        for(Ingredient ingredient : inputItems){
            if(ingredient.test(input.getItem(0)))
                return true;
        }
        return false;
    }

    public static void loadRecipes(@NotNull RecipeManager manager){
        Validator.clear();
        for(RecipeHolder<DyeLiquifierRecipe> recipeHolder : manager.getAllRecipesFor(RecipesRegistry.DYE_LIQUIFIER_RECIPE_TYPE.get())) {
            Validator.add(recipeHolder.value().getInputItems());
        }
    }

    public static class Validator {
        private static final List<Ingredient> validIngredients = new ArrayList<>();

        public static boolean isIngredientValid(ItemStack item){
            for(Ingredient ingredient : validIngredients){
                if(ingredient.test(item))
                    return true;
            }
            return false;
        }

        protected static void add(List<Ingredient> items){
            validIngredients.addAll(items);
        }

        protected static void clear(){
            validIngredients.clear();
        }
    }
}
