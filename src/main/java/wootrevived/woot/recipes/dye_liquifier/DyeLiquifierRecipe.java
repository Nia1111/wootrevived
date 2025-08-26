package wootrevived.woot.recipes.dye_liquifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wootrevived.woot.registries.RecipesRegistry;
import wootrevived.woot.util.recipes.WootRecipe;

import java.util.ArrayList;
import java.util.List;

public class DyeLiquifierRecipe extends WootRecipe {
    public static final MapCodec<DyeLiquifierRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Codec.INT.fieldOf("energy").forGetter(DyeLiquifierRecipe::getEnergy),
            Codec.INT.fieldOf("red").forGetter(DyeLiquifierRecipe::getRed),
            Codec.INT.fieldOf("yellow").forGetter(DyeLiquifierRecipe::getYellow),
            Codec.INT.fieldOf("blue").forGetter(DyeLiquifierRecipe::getBlue),
            Codec.INT.fieldOf("white").forGetter(DyeLiquifierRecipe::getWhite),
            Ingredient.CODEC.listOf().fieldOf("inputIngredients").forGetter(DyeLiquifierRecipe::getInputItems)
    ).apply(inst, DyeLiquifierRecipe::new));

    private final int red;
    private final int yellow;
    private final int blue;
    private final int white;

    public DyeLiquifierRecipe(int energy, int red, int yellow, int blue, int white, @Nullable List<Ingredient> inputItems) {
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
        return red;
    }

    public int getYellow() {
        return yellow;
    }

    public int getBlue() {
        return blue;
    }

    public int getWhite() {
        return white;
    }

    @Override
    public boolean matches(Container container, Level level) {
        for(Ingredient ingredient : inputItems){
            if(ingredient.test(container.getItem(0)))
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
