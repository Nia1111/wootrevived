package wootrevived.woot.recipes.dye_liquifier;

import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;
import wootrevived.woot.Woot;
import wootrevived.woot.registries.BlocksRegistry;
import wootrevived.woot.registries.RecipesRegistry;
import wootrevived.woot.util.recipes.WootFinishedRecipe;

import java.util.List;
import java.util.function.Consumer;

public class DyeLiquifierRecipeBuilder {
    private int energy;
    private int red;
    private int yellow;
    private int blue;
    private int white;
    private int multiply;
    private Ingredient ingredient;

    protected DyeLiquifierRecipeBuilder() {
        this.multiply = 1;
    }

    public static DyeLiquifierRecipeBuilder dyeLiquifierRecipe(){
        return new DyeLiquifierRecipeBuilder();
    }

    public DyeLiquifierRecipeBuilder energy(int energy){
        this.energy = energy;
        return this;
    }

    public DyeLiquifierRecipeBuilder red(int red){
        this.red = red;
        return this;
    }

    public DyeLiquifierRecipeBuilder yellow(int yellow){
        this.yellow = yellow;
        return this;
    }

    public DyeLiquifierRecipeBuilder blue(int blue){
        this.blue = blue;
        return this;
    }

    public DyeLiquifierRecipeBuilder white(int white){
        this.white = white;
        return this;
    }

    public DyeLiquifierRecipeBuilder multiply(int multiply){
        this.multiply = multiply;
        return this;
    }

    public DyeLiquifierRecipeBuilder ingredient(Ingredient ingredient){
        this.ingredient = ingredient;
        return this;
    }

    public void save(Consumer<FinishedRecipe> consumer, String path){
        consumer.accept(new Result(
                ResourceLocation.tryBuild(Woot.MOD_ID, BlocksRegistry.DYE_LIQUIFIER_TAG + "/" + path),
                energy, red * multiply, yellow * multiply, blue * multiply, white * multiply, ingredient
        ));
    }

    public static class Result extends WootFinishedRecipe {
        private final int red;
        private final int yellow;
        private final int blue;
        private final int white;

        protected Result(ResourceLocation recipeId, int energy, int red, int yellow, int blue, int white, Ingredient ingredient) {
            super(recipeId, energy, List.of(ingredient), null, null, null);
            this.red = red;
            this.yellow = yellow;
            this.blue = blue;
            this.white = white;
        }
        @Override
        public void serializeRecipeData(JsonObject json) {
            super.serializeRecipeData(json);
            json.addProperty("red", this.red);
            json.addProperty("yellow", this.yellow);
            json.addProperty("blue", this.blue);
            json.addProperty("white", this.white);
        }

        @Override
        public @NotNull RecipeSerializer<?> getType() {
            return RecipesRegistry.DYE_LIQUIFIER_RECIPE_SERIALIZER.get();
        }
    }
}
