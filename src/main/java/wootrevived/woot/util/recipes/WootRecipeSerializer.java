package wootrevived.woot.util.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WootRecipeSerializer<T extends WootRecipe> implements RecipeSerializer<T> {
    protected final MapCodec<T> codec;
    protected final IFactory<T> factory;

    public WootRecipeSerializer(MapCodec<T> codec, IFactory<T> factory) {
        this.codec = codec;
        this.factory = factory;
    }

    @Override
    public @NotNull Codec<T> codec() {
        return codec.codec();
    }

    @Override
    public @NotNull T fromNetwork(@NotNull FriendlyByteBuf buffer) {
        int energy = buffer.readVarInt();

        int lenInputItems = buffer.readVarInt();
        ArrayList<Ingredient> inputItems = new ArrayList<>(lenInputItems);
        for(int i = 0; i < lenInputItems; i++){
            inputItems.add(Ingredient.fromNetwork(buffer));
        }

        int lenInputFluids = buffer.readVarInt();
        ArrayList<FluidStack> inputFluids = new ArrayList<>(lenInputFluids);
        for(int i = 0; i < lenInputFluids; i++){
            inputFluids.add(buffer.readFluidStack());
        }

        ItemStack outputItem = null;
        if(buffer.readBoolean()){
            outputItem = buffer.readItem();
        }

        FluidStack outputFluid = null;
        if(buffer.readBoolean()){
            outputFluid = buffer.readFluidStack();
        }

        return factory.create(energy, inputItems, inputFluids, outputItem, outputFluid);
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull T recipe) {
        buffer.writeVarInt(recipe.energy);

        int lenInputItems = recipe.inputItems.size();
        buffer.writeVarInt(lenInputItems);
        for(int i = 0; i < lenInputItems; ++i){
            recipe.inputItems.get(i).toNetwork(buffer);
        }

        int lenInputFluids = recipe.inputFluids.size();
        buffer.writeVarInt(lenInputFluids);
        for(int i = 0; i < lenInputFluids; ++i){
            buffer.writeFluidStack(recipe.inputFluids.get(i));
        }

        buffer.writeBoolean(recipe.outputItem != null);
        if(recipe.outputItem != null){
            buffer.writeItem(recipe.outputItem);
        }

        buffer.writeBoolean(recipe.outputFluid != null);
        if(recipe.outputFluid != null){
            buffer.writeFluidStack(recipe.outputFluid);
        }
    }

    public interface IFactory<T extends WootRecipe> {
        T create(int energy, List<Ingredient> inputItems, List<FluidStack> inputFluids, ItemStack outputItem, FluidStack outputFluid);
    }
}
