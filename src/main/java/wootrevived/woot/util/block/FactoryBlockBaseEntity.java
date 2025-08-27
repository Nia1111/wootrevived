package wootrevived.woot.util.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wootrevived.woot.data.FactoryBlockData;

import java.util.Objects;
import java.util.Optional;

public class FactoryBlockBaseEntity extends BlockEntity {
    public FactoryBlockBaseEntity(BlockEntityType<?> entity, BlockPos pos, BlockState state) {
        super(entity, pos, state);
    }

    protected BlockPos heartPos = null;

    public boolean isHeartPosEmpty(){
        return heartPos == null;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isSameHeartPos(BlockPos blockPos){
        return Objects.equals(blockPos, heartPos);
    }

    public void setHeartPos(BlockPos blockPos){
        if(Objects.equals(blockPos, heartPos))
            return;
        heartPos = blockPos;
        setChanged();
    }

    private FactoryBlockData.Component getComponent(){
        return new FactoryBlockData.Component(
                Optional.ofNullable(heartPos)
        );
    }

    private void setComponent(FactoryBlockData.Component component){
        heartPos = component.heartPos().orElse(null);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider){
        super.saveAdditional(tag, provider);
        FactoryBlockData.CODEC.encodeStart(NbtOps.INSTANCE, getComponent()).result().ifPresent(t -> {
            if(t instanceof CompoundTag compound) tag.merge(compound);
        });
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider){
        super.loadAdditional(tag, provider);
        FactoryBlockData.CODEC.parse(provider.createSerializationContext(NbtOps.INSTANCE), tag).result().ifPresent(this::setComponent);
    }

    @NotNull
    @Override
    public CompoundTag getUpdateTag(HolderLookup.@NotNull Provider provider){
        CompoundTag tag = super.getUpdateTag(provider);
        saveAdditional(tag, provider);
        return tag;
    }

    @Override
    public void handleUpdateTag(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider lookupProvider){
        super.handleUpdateTag(tag, lookupProvider);
        loadAdditional(tag, lookupProvider);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket(){
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void setChanged() {
        super.setChanged();

        if(this.level == null || this.level.isClientSide) return;
        this.level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
    }
}
