package wootrevived.woot.datagen.recipes;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import wootrevived.woot.datagen.Recipes;
import wootrevived.woot.recipes.dye_liquifier.DyeLiquifierRecipeBuilder;
import wootrevived.woot.util.common.DyeMakeup;

import java.util.Locale;
import java.util.function.Consumer;

public class DyeLiquifier {
    public static void registerRecipes(Recipes recipes, Consumer<FinishedRecipe> consumer){
        for (DyeMakeup d : DyeMakeup.values()) {
            DyeLiquifierRecipeBuilder.dyeLiquifierRecipe()
                    .ingredient(Ingredient.of(d.getItemTag()))
                    .energy(500)
                    .red(d.getRed())
                    .yellow(d.getYellow())
                    .blue(d.getBlue())
                    .white(d.getWhite())
                    .save(consumer, d.name().toLowerCase(Locale.ROOT));
        }

        class VanillaDyes {
            final Item item;
            final DyeMakeup dyeMakeup;
            final int multiply;
            final String name;
            public VanillaDyes(Item item, DyeMakeup dyeMakeup, int multiply, String name) {
                this.item = item;
                this.dyeMakeup = dyeMakeup;
                this.multiply = multiply;
                this.name = name;
            }
        }

        VanillaDyes[] dyes = {
                new VanillaDyes(Items.BONE_MEAL, DyeMakeup.WHITE, 1, "bone_meal"),
                new VanillaDyes(Items.LILY_OF_THE_VALLEY, DyeMakeup.WHITE, 1, "lily_of_the_valley"),
                new VanillaDyes(Items.OXEYE_DAISY, DyeMakeup.LIGHTGRAY, 1, "oxeye_daisy"),
                new VanillaDyes(Items.AZURE_BLUET, DyeMakeup.LIGHTGRAY, 1, "azure_bluet"),
                new VanillaDyes(Items.WHITE_TULIP, DyeMakeup.LIGHTGRAY, 1, "white_tulip"),
                new VanillaDyes(Items.INK_SAC, DyeMakeup.BLACK, 1, "ink_sac"),
                new VanillaDyes(Items.WITHER_ROSE, DyeMakeup.BLACK, 1, "wither_rose"),
                new VanillaDyes(Items.COCOA_BEANS, DyeMakeup.BROWN, 1, "cocoa_beans"),
                new VanillaDyes(Items.RED_TULIP, DyeMakeup.RED, 1, "red_tulip"),
                new VanillaDyes(Items.BEETROOT, DyeMakeup.RED, 1, "beetroot"),
                new VanillaDyes(Items.POPPY, DyeMakeup.RED, 1, "poppy"),
                new VanillaDyes(Items.ROSE_BUSH, DyeMakeup.RED, 2, "rose_bush"),
                new VanillaDyes(Items.TORCHFLOWER, DyeMakeup.ORANGE, 1, "torchflower"),
                new VanillaDyes(Items.ORANGE_TULIP, DyeMakeup.ORANGE, 1, "orange_tulip"),
                new VanillaDyes(Items.DANDELION, DyeMakeup.YELLOW, 1, "dandelion"),
                new VanillaDyes(Items.SUNFLOWER, DyeMakeup.YELLOW, 2, "sunflower"),
                new VanillaDyes(Items.SEA_PICKLE, DyeMakeup.LIME, 1, "sea_pickle"),
                new VanillaDyes(Items.CACTUS, DyeMakeup.GREEN, 1, "cactus"),
                new VanillaDyes(Items.PITCHER_PLANT, DyeMakeup.CYAN, 2, "pitcher_plant"),
                new VanillaDyes(Items.BLUE_ORCHID, DyeMakeup.LIGHTBLUE, 1, "blue_orchid"),
                new VanillaDyes(Items.CORNFLOWER, DyeMakeup.BLUE, 1, "cornflower"),
                new VanillaDyes(Items.ALLIUM, DyeMakeup.MAGENTA, 1, "allium"),
                new VanillaDyes(Items.LILAC, DyeMakeup.MAGENTA, 2, "lilac"),
                new VanillaDyes(Items.PINK_PETALS, DyeMakeup.PINK, 1, "pink_petals"),
                new VanillaDyes(Items.PINK_TULIP, DyeMakeup.PINK, 1, "pink_tulip"),
                new VanillaDyes(Items.PEONY, DyeMakeup.PINK, 2, "peony"),
        };

        for (VanillaDyes d : dyes) {
            DyeLiquifierRecipeBuilder.dyeLiquifierRecipe()
                    .ingredient(Ingredient.of(d.item))
                    .energy(500)
                    .red(d.dyeMakeup.getRed())
                    .yellow(d.dyeMakeup.getYellow())
                    .blue(d.dyeMakeup.getBlue())
                    .white(d.dyeMakeup.getWhite())
                    .multiply(d.multiply)
                    .save(consumer, d.name);
        }
    }
}
