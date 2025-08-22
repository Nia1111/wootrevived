package wootrevived.woot.recipes.enchanted_liquifier;

import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import wootrevived.woot.registries.RecipesRegistry;
import wootrevived.woot.util.recipes.WootRecipe;

import java.util.List;

// Recipe class for JEI
public class EnchantedLiquifierRecipe extends WootRecipe {
    public EnchantedLiquifierRecipe(int energy, List<Ingredient> inputItems, FluidStack outputFluid) {
        super(null, energy, inputItems, null, null, outputFluid);
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        throw new IllegalStateException("Enchanted Serializer shouldn't exist");
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return RecipesRegistry.ENCHANTED_LIQUIFIER_RECIPE_TYPE.get();
    }

    public Ingredient getInputIngredient(){
        return inputItems.get(0);
    }

    @Override
    public boolean matches(@NotNull Container container, @NotNull Level level) {
        return false;
    }
}
