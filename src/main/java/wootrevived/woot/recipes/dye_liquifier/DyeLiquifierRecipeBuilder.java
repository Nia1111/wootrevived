package wootrevived.woot.recipes.dye_liquifier;

import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import wootrevived.woot.Woot;
import wootrevived.woot.registries.BlocksRegistry;

import java.util.List;

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

    public void save(RecipeOutput recipeOutput, String path){
        recipeOutput.accept(
                ResourceLocation.tryBuild(Woot.MOD_ID, BlocksRegistry.DYE_LIQUIFIER_TAG + "/" + path),
                new DyeLiquifierRecipe(energy, red * multiply, yellow * multiply, blue * multiply, white * multiply, List.of(ingredient)),
                null
        );
    }
}
