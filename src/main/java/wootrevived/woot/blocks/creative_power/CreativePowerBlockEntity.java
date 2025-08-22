package wootrevived.woot.blocks.creative_power;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wootrevived.woot.registries.BlocksRegistry;

public class CreativePowerBlockEntity extends BlockEntity implements BlockEntityTicker<BlockEntity> {
    public CreativePowerBlockEntity(BlockPos pos, BlockState state) {
        super(BlocksRegistry.CREATIVE_POWER_BLOCK_ENTITY.get(), pos, state);
    }

    public static void ticker(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity){
        if(blockEntity instanceof CreativePowerBlockEntity creativePowerBlockEntity){
            creativePowerBlockEntity.tick(level, pos, state, blockEntity);
        }
    }

    @Override
    public void tick(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull BlockEntity blockEntity) {
        if (level.isClientSide)
            return;

        for (Direction facing : Direction.values()) {
            BlockEntity be = level.getBlockEntity(getBlockPos().relative(facing));
            if (be != null) {
                be.getCapability(ForgeCapabilities.ENERGY, facing.getOpposite()).ifPresent(h -> {
                    if (h.canReceive())
                        h.receiveEnergy(1000, false);
                });
            }
        }
    }

    private final LazyOptional<IEnergyStorage> energyStorage = LazyOptional.of(this::createEnergy);
    private EnergyStorage createEnergy() {
        EnergyStorage es = new EnergyStorage(Integer.MAX_VALUE);
        es.receiveEnergy(Integer.MAX_VALUE, false);
        return es;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY)
            return energyStorage.cast();
        return super.getCapability(cap, side);
    }
}
