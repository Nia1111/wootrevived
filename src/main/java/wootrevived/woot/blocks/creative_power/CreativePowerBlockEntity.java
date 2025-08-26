package wootrevived.woot.blocks.creative_power;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
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
            IEnergyStorage storage = level.getCapability(Capabilities.EnergyStorage.BLOCK, getBlockPos().relative(facing), facing.getOpposite());
            if(storage == null)
                continue;

            if(storage.canReceive())
                storage.receiveEnergy(1000, false);
        }
    }

    private final EnergyStorage energyHandler = createEnergy();
    private EnergyStorage createEnergy() {
        EnergyStorage es = new EnergyStorage(Integer.MAX_VALUE);
        es.receiveEnergy(Integer.MAX_VALUE, false);
        return es;
    }

    public static IEnergyStorage getEnergyStorageCapability(CreativePowerBlockEntity blockEntity, Direction side){
        return blockEntity.energyHandler;
    }
}
