package wootrevived.woot.datagen.recipes;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import wootrevived.woot.datagen.Recipes;
import wootrevived.woot.recipes.fluid_infuser.FluidInfuserRecipeBuilder;
import wootrevived.woot.registries.FluidsRegistry;
import wootrevived.woot.registries.ItemsRegistry;

import java.util.function.Consumer;

public class FluidInfuser {
    public static void registerRecipes(Recipes recipes, Consumer<FinishedRecipe> consumer) {
        FluidInfuserRecipeBuilder.fluidInfuserRecipe(FluidsRegistry.SOURCE_VITALITY_FUEL_FLUID.get(), 1250)
                .ingredient(Ingredient.of(Blocks.MAGMA_BLOCK))
                .fluid(FluidsRegistry.SOURCE_ENCHANTED_FLUID.get())
                .energy(1000)
                .save(consumer, "vitality_fuel_enchanted_1");

        FluidInfuserRecipeBuilder.fluidInfuserRecipe(FluidsRegistry.SOURCE_VITALITY_FUEL_FLUID.get(), 1450)
                .ingredient(Ingredient.of(Blocks.END_STONE))
                .fluid(FluidsRegistry.SOURCE_ENCHANTED_FLUID.get())
                .energy(1000)
                .save(consumer, "vitality_fuel_enchanted_2");

        class IngredientRecipe {
            final Ingredient ingredient;
            final int outputAmount;

            public IngredientRecipe(Ingredient ingredient, int outputAmount) {
                this.ingredient = ingredient;
                this.outputAmount = outputAmount;
            }
        }

        IngredientRecipe[] vitalityFuelIngredients = new IngredientRecipe[]{
                new IngredientRecipe(Ingredient.of(ItemsRegistry.XP_SHARD_ITEM.get()), 1000),
                new IngredientRecipe(Ingredient.of(ItemsRegistry.XP_SPLINTER_ITEM.get()), 100),
                new IngredientRecipe(Ingredient.of(Items.REDSTONE), 1000),
                new IngredientRecipe(Ingredient.of(ItemsRegistry.COPPER_SHARD_ITEM.get()), 1000),
                new IngredientRecipe(Ingredient.of(ItemsRegistry.IRON_SHARD_ITEM.get()), 1500),
                new IngredientRecipe(Ingredient.of(ItemsRegistry.GOLD_SHARD_ITEM.get()), 2000),
        };

        for (int i = 0; i < vitalityFuelIngredients.length; i++) {
            FluidInfuserRecipeBuilder.fluidInfuserRecipe(FluidsRegistry.SOURCE_VITALITY_FUEL_FLUID.get(), vitalityFuelIngredients[i].outputAmount)
                    .ingredient(vitalityFuelIngredients[i].ingredient)
                    .fluid(FluidsRegistry.SOURCE_MOB_TEARS_FLUID.get())
                    .energy(1000)
                    .save(consumer, "vitality_fuel_" + i);
        }

        IngredientRecipe[] mobTearsIngredients = new IngredientRecipe[]{
                new IngredientRecipe(Ingredient.of(Items.ROTTEN_FLESH), 1000),
                new IngredientRecipe(Ingredient.of(Items.BONE), 1000),
                new IngredientRecipe(Ingredient.of(Items.SPIDER_EYE), 1000),
                new IngredientRecipe(Ingredient.of(Items.BLAZE_POWDER), 1000),
                new IngredientRecipe(Ingredient.of(Items.FERMENTED_SPIDER_EYE), 1500),
                new IngredientRecipe(Ingredient.of(Items.BLAZE_ROD), 2000),
                new IngredientRecipe(Ingredient.of(Items.ENDER_PEARL), 4000),
                new IngredientRecipe(Ingredient.of(Items.ENDER_EYE), 5000),
                new IngredientRecipe(Ingredient.of(Items.DRAGON_BREATH), 5000),
                new IngredientRecipe(Ingredient.of(Items.SHULKER_SHELL), 6000),
                new IngredientRecipe(Ingredient.of(Items.NETHER_STAR), 10000),
        };

        for (int i = 0; i < mobTearsIngredients.length; i++) {
            FluidInfuserRecipeBuilder.fluidInfuserRecipe(FluidsRegistry.SOURCE_MOB_TEARS_FLUID.get(), mobTearsIngredients[i].outputAmount)
                    .ingredient(mobTearsIngredients[i].ingredient)
                    .fluid(Fluids.WATER)
                    .energy(1000)
                    .save(consumer, "mob_tears_" + i);
        }
    }
}
