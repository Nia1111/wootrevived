package wootrevived.woot.guide.recipes;

import guideme.compiler.tags.RecipeTypeMappingSupplier;
import wootrevived.woot.registries.RecipesRegistry;

public class WootRecipeTypeMapping implements RecipeTypeMappingSupplier {
    @Override
    public void collect(RecipeTypeMappings mappings){
        mappings.add(RecipesRegistry.ITEM_INFUSER_RECIPE_TYPE.get(), LytItemInfuserRecipe::new);
        mappings.add(RecipesRegistry.FLUID_INFUSER_RECIPE_TYPE.get(), LytFluidInfuserRecipe::new);
        mappings.add(RecipesRegistry.DYE_LIQUIFIER_RECIPE_TYPE.get(), LytDyeLiquifierRecipe::new);
    }
}
