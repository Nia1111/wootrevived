package wootrevived.woot.blocks.creative_power;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wootrevived.woot.registries.BlocksRegistry;

public class CreativePowerBlock extends Block implements EntityBlock {
    public CreativePowerBlock() {
        super(Properties.of()
                .mapColor(MapColor.METAL)
                .sound(SoundType.METAL));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return BlocksRegistry.CREATIVE_POWER_BLOCK_ENTITY.get().create(pos, state);
    }

    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        if(level.isClientSide()) return null;
        return blockEntityType == BlocksRegistry.CREATIVE_POWER_BLOCK_ENTITY.get() ? CreativePowerBlockEntity::ticker : null;
    }
}
