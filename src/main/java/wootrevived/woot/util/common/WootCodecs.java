package wootrevived.woot.util.common;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class WootCodecs {
    public static Codec<List<EnumMap<MachineSide, MachineSideProperty>>> MACHINE_PROPERTIES_CODEC = Codec.unboundedMap(
            StringRepresentable.fromEnum(MachineSide::values),
            StringRepresentable.fromEnum(MachineSideProperty::values)
    ).xmap(m -> {
        EnumMap<MachineSide, MachineSideProperty> em = new EnumMap<>(MachineSide.class);
        em.putAll(m);
        return em;
    }, HashMap::new).listOf();

    public static StreamCodec<FriendlyByteBuf, List<EnumMap<MachineSide, MachineSideProperty>>> MACHINE_PROPERTIES_STREAM_CODEC = ByteBufCodecs.map(
            (size) -> new EnumMap<>(MachineSide .class),
            NeoForgeStreamCodecs.enumCodec(MachineSide.class),
            NeoForgeStreamCodecs.enumCodec(MachineSideProperty .class)
    ).apply(ByteBufCodecs.collection(NonNullList::createWithCapacity));

    public static final Codec<Float> NON_NEGATIVE_FLOAT = floatRangeWithMessage(
            0.0F, Float.MAX_VALUE, f -> "Value must be non-negative: " + f
    );

    private static Codec<Float> floatRangeWithMessage(float min, float max, Function<Float, String> msg) {
        return Codec.FLOAT.validate(
                f -> f.compareTo(min) >= 0 && f.compareTo(max) <= 0
                        ? DataResult.success(f)
                        : DataResult.error(() -> msg.apply(f))
        );
    }
}
