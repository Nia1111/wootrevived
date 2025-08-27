package wootrevived.woot.recipes.enchanted_liquifier;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wootrevived.woot.registries.RecipesRegistry;
import wootrevived.woot.util.recipes.WootRecipe;
import wootrevived.woot.util.recipes.WootRecipeInput;

import java.util.ArrayList;
import java.util.List;

// Recipe class for JEI
public class EnchantedLiquifierRecipe extends WootRecipe {
    public EnchantedLiquifierRecipe(int energy, @Nullable List<Ingredient> inputItems, FluidStack outputFluid) {
        super(energy, inputItems, null, null, outputFluid);
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
    public boolean matches(@NotNull WootRecipeInput input, @NotNull Level level) {
        return false;
    }

    public static List<Enchantment> enchantments = new ArrayList<>();

    public static void loadRecipes(@NotNull ServerLevel level){
        RegistryAccess accessor = level.registryAccess();
        HolderLookup.RegistryLookup<Enchantment> lookup = accessor.lookupOrThrow(Registries.ENCHANTMENT);

        lookup.listElements().forEach(enchantment -> {
            enchantments.add(enchantment.value());
        });
    }
}
