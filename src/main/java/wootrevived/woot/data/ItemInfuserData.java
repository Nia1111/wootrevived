package wootrevived.woot.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import wootrevived.woot.util.common.MachineSide;
import wootrevived.woot.util.common.MachineSideProperty;
import wootrevived.woot.util.entity.WootTags;
import wootrevived.woot.util.helper.MachinePropertiesDataHelper;

import java.util.EnumMap;
import java.util.List;

public final class ItemInfuserData {
    public static final String ID = "item_infuser_data";

    public static final Codec<Component> CODEC = RecordCodecBuilder.create(inst ->
            inst.group(
                    Codec.INT.fieldOf(WootTags.ENERGY_TAG).forGetter(Component::energy),
                    FluidStack.OPTIONAL_CODEC.fieldOf(WootTags.INPUT_TANK_TAG).forGetter(Component::inputFluid),
                    MachinePropertiesDataHelper.CODEC.fieldOf(WootTags.DirectionProperties.LIST).forGetter(Component::listMachineProperties)
            ).apply(inst, Component::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, Component> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, Component::energy,
            FluidStack.OPTIONAL_STREAM_CODEC, Component::inputFluid,
            MachinePropertiesDataHelper.STREAM_CODEC, Component::listMachineProperties,
            Component::new
    );

    public record Component(
            int energy,
            @NotNull FluidStack inputFluid,
            List<EnumMap<MachineSide, MachineSideProperty>> listMachineProperties
    ) {}
}
