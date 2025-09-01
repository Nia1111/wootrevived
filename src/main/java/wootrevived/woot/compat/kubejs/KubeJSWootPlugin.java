package wootrevived.woot.compat.kubejs;

import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaRegistry;
import wootrevived.woot.Woot;
import wootrevived.woot.compat.kubejs.recipes.DyeLiquifierRecipeJS;
import wootrevived.woot.compat.kubejs.recipes.FluidInfuserRecipeJS;
import wootrevived.woot.compat.kubejs.recipes.ItemInfuserRecipeJS;
import wootrevived.woot.compat.kubejs.recipes.StygianAnvilRecipeJS;
import wootrevived.woot.registries.BlocksRegistry;

public class KubeJSWootPlugin implements KubeJSPlugin {
    @Override
    public void registerRecipeSchemas(RecipeSchemaRegistry registry) {
        registry.register(Woot.location(BlocksRegistry.DYE_LIQUIFIER_TAG), DyeLiquifierRecipeJS.SCHEMA);
        registry.register(Woot.location(BlocksRegistry.FLUID_INFUSER_TAG), FluidInfuserRecipeJS.SCHEMA);
        registry.register(Woot.location(BlocksRegistry.ITEM_INFUSER_TAG), ItemInfuserRecipeJS.SCHEMA);
        registry.register(Woot.location(BlocksRegistry.STYGIAN_ANVIL_TAG), StygianAnvilRecipeJS.SCHEMA);
    }
}
