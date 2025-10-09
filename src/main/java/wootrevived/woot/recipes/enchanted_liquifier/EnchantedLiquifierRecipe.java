package wootrevived.woot.recipes.enchanted_liquifier;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import wootrevived.woot.registries.RecipesRegistry;
import wootrevived.woot.util.recipes.WootRecipeInput;

import java.util.ArrayList;
import java.util.List;

// Recipe class for JEI
public class EnchantedLiquifierRecipe implements Recipe<WootRecipeInput> {
    private final int energy;
    private final Ingredient ingredient;
    private final FluidStack output;

    public EnchantedLiquifierRecipe(int energy, Ingredient ingredient, FluidStack output) {
        this.energy = energy;
        this.ingredient = ingredient;
        this.output = output;
    }

    public Ingredient getIngredient(){
        return ingredient;
    }

    public FluidStack getOutput(){
        return this.output;
    }

    public int getEnergy() {
        return energy;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        throw new IllegalStateException("Enchanted Serializer shouldn't exist");
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return RecipesRegistry.ENCHANTED_LIQUIFIER_RECIPE_TYPE.get();
    }

    @Override
    public boolean matches(@NotNull WootRecipeInput input, @NotNull Level level) {
        return false;
    }

    public static List<Enchantment> enchantments = new ArrayList<>();

    public static void loadRecipes(@NotNull Level level){
        RegistryAccess accessor = level.registryAccess();
        HolderLookup.RegistryLookup<Enchantment> lookup = accessor.lookupOrThrow(Registries.ENCHANTMENT);

        enchantments.clear();

        lookup.listElements().forEach(enchantment -> {
            enchantments.add(enchantment.value());
        });
    }

    @Override
    public @NotNull ItemStack assemble(WootRecipeInput input, HolderLookup.@NotNull Provider provider){
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
}
