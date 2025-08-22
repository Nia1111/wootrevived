package wootrevived.woot.network;

import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import wootrevived.woot.util.common.MachineSide;
import wootrevived.woot.util.common.MachineSideProperty;
import wootrevived.woot.util.common.RedstoneMode;
import wootrevived.woot.util.entity.WootMachineBlockEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public record WootMachineUpdate(BlockPos blockPos, RedstoneMode redstoneMode,
                                List<Map<MachineSide, MachineSideProperty>> listMachineProperties) {

    public static WootMachineUpdate decode(FriendlyByteBuf buf) {
        int size = buf.readVarInt();
        List<Map<MachineSide, MachineSideProperty>> listMachineProperties = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Map<MachineSide, MachineSideProperty> machineSideProperties = Maps.newEnumMap(MachineSide.class);
            for (int j = 0; j < MachineSide.values().length; j++) {
                machineSideProperties.put(buf.readEnum(MachineSide.class), buf.readEnum(MachineSideProperty.class));
            }
            listMachineProperties.add(machineSideProperties);
        }
        return new WootMachineUpdate(buf.readBlockPos(), buf.readEnum(RedstoneMode.class), listMachineProperties);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeVarInt(listMachineProperties.size());
        for (Map<MachineSide, MachineSideProperty> machineProperties : listMachineProperties) {
            for (Map.Entry<MachineSide, MachineSideProperty> entry : machineProperties.entrySet()) {
                buf.writeEnum(entry.getKey());
                buf.writeEnum(entry.getValue());
            }
        }
        buf.writeBlockPos(blockPos);
        buf.writeEnum(redstoneMode);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer sender = ctx.get().getSender();
            if (sender == null || sender.level() == null) return;
            if (!sender.level().isLoaded(blockPos)) return;

            BlockEntity blockEntity = sender.level().getBlockEntity(blockPos);
            if (blockEntity instanceof WootMachineBlockEntity wootMachineBlockEntity && wootMachineBlockEntity.canPlayerAccess(sender)) {
                wootMachineBlockEntity.handleNewState(this);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
