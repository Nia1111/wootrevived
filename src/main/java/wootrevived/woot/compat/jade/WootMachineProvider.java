package wootrevived.woot.compat.jade;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.StreamServerDataProvider;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.BoxStyle;
import snownee.jade.api.ui.IElementHelper;
import wootrevived.woot.Woot;
import wootrevived.woot.util.entity.WootMachineBlockEntity;

public enum WootMachineProvider implements IBlockComponentProvider, StreamServerDataProvider<BlockAccessor, WootMachineProvider.Data> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        Data data = this.decodeFromData(accessor).orElse(null);
        if (data != null) {
            IElementHelper helper = IElementHelper.get();
            BoxStyle.GradientBorder box = BoxStyle.getTransparent().clone();
            box.bgColor = 0x88000000;
            tooltip.add(helper.progress(data.progress / 100F, null, helper.progressStyle(), box, false));
        }
    }

    @Override
    public Data streamData(BlockAccessor accessor) {
        WootMachineBlockEntity entity = (WootMachineBlockEntity)accessor.getBlockEntity();
        return new Data(entity.calculateProgress());
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, Data> streamCodec() {
        return Data.STREAM_CODEC;
    }

    @Override
    public ResourceLocation getUid() {
        return Woot.location("machines");
    }

    public record Data(int progress) {
        public static final StreamCodec<RegistryFriendlyByteBuf, Data> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.VAR_INT, Data::progress,
                Data::new
        );
    }
}
