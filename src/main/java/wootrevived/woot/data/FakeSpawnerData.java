package wootrevived.woot.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;
import wootrevived.woot.util.entity.WootTags;

import java.util.Optional;

public final class FakeSpawnerData {
    public static final String ID = "fake_spawner_data";

    public static final Codec<Component> CODEC = RecordCodecBuilder.create(inst ->
            inst.group(
                    CompoundTag.CODEC.optionalFieldOf(WootTags.MOB_TAG).forGetter(Component::mobTag),
                    Codec.INT.fieldOf(WootTags.Factory.NUMBER_OF_SIMULATIONS).forGetter(Component::numberOfSimulations),
                    Codec.INT.fieldOf(WootTags.Factory.VITALITY_COST).forGetter(Component::vitalityCost),
                    Codec.INT.fieldOf(WootTags.Factory.TOTAL_DRAINED).forGetter(Component::totalDrained),
                    Codec.DOUBLE.fieldOf(WootTags.Factory.PER_TICK_RATIO).forGetter(Component::perTickRatio),
                    Codec.DOUBLE.fieldOf(WootTags.Factory.ACCUMULATOR).forGetter(Component::accumulator)
            ).apply(inst, Component::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, Component> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.optional(ByteBufCodecs.compoundTagCodec(NbtAccounter::unlimitedHeap)), Component::mobTag,
            ByteBufCodecs.INT, Component::numberOfSimulations,
            ByteBufCodecs.INT, Component::vitalityCost,
            ByteBufCodecs.INT, Component::totalDrained,
            ByteBufCodecs.DOUBLE, Component::perTickRatio,
            ByteBufCodecs.DOUBLE, Component::accumulator,
            Component::new
    );

    public record Component(
            @NotNull Optional<CompoundTag> mobTag,
            int numberOfSimulations,
            int vitalityCost,
            int totalDrained,
            double perTickRatio,
            double accumulator
    ) {}
}
