package wootrevived.woot.util.block;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import wootrevived.woot.util.entity.WootTags;

import java.util.Objects;

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

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag){
        super.saveAdditional(tag);

        if(this.heartPos != null){
            tag.putLong(WootTags.Factory.HEART_POS, heartPos.asLong());
        }
    }

    @Override
    public void load(@NotNull CompoundTag tag){
        super.load(tag);

        if(tag.contains(WootTags.Factory.HEART_POS))
            heartPos = BlockPos.of(tag.getLong(WootTags.Factory.HEART_POS));
        else
            heartPos = null;
    }

    @NotNull
    @Override
    public CompoundTag getUpdateTag(){
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag){
        super.handleUpdateTag(tag);
        load(tag);
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket(){
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void setChanged() {
        super.setChanged();

        if(this.level == null || this.level.isClientSide) return;
        this.level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
    }
}
