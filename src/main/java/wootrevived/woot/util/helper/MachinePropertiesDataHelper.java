package wootrevived.woot.util.helper;

import com.mojang.serialization.Codec;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import wootrevived.woot.util.common.MachineSide;
import wootrevived.woot.util.common.MachineSideProperty;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

public class MachinePropertiesDataHelper {
    public static Codec<List<EnumMap<MachineSide, MachineSideProperty>>> CODEC = Codec.unboundedMap(
            StringRepresentable.fromEnum(MachineSide::values),
            StringRepresentable.fromEnum(MachineSideProperty::values)
    ).xmap(m -> {
        EnumMap<MachineSide, MachineSideProperty> em = new EnumMap<>(MachineSide.class);
        em.putAll(m);
        return em;
    }, HashMap::new).listOf();

    public static StreamCodec<FriendlyByteBuf, List<EnumMap<MachineSide, MachineSideProperty>>> STREAM_CODEC = ByteBufCodecs.map(
            (size) -> new EnumMap<>(MachineSide .class),
            NeoForgeStreamCodecs.enumCodec(MachineSide.class),
            NeoForgeStreamCodecs.enumCodec(MachineSideProperty .class)
    ).apply(ByteBufCodecs.collection(NonNullList::createWithCapacity));
}
