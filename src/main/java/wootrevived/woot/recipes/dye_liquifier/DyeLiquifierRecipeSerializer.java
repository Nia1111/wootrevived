package wootrevived.woot.recipes.dye_liquifier;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class DyeLiquifierRecipeSerializer implements RecipeSerializer<DyeLiquifierRecipe> {
    @Override
    public @NotNull MapCodec<DyeLiquifierRecipe> codec() {
        return DyeLiquifierRecipe.CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, DyeLiquifierRecipe> streamCodec() {
        return DyeLiquifierRecipe.STREAM_CODEC;
    }
}
