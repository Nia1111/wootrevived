package wootrevived.woot.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import wootrevived.woot.util.entity.WootTags;

public final class CellData {
    public static final String ID = "cell_data";

    public static final Codec<Component> CODEC = RecordCodecBuilder.create(inst ->
            inst.group(
                FluidStack.OPTIONAL_CODEC.fieldOf(WootTags.INPUT_TANK_TAG).forGetter(Component::tankFluid)
            ).apply(inst, Component::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, Component> STREAM_CODEC = StreamCodec.composite(
            FluidStack.OPTIONAL_STREAM_CODEC, Component::tankFluid,
            Component::new
    );

    public record Component(
            @NotNull FluidStack tankFluid
    ) {}
}