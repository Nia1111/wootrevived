package wootrevived.woot.blocks.creative_tank;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wootrevived.woot.registries.BlocksRegistry;

public class CreativeTankBlock extends Block implements EntityBlock {
    public CreativeTankBlock() {
        super(Properties.of()
                .mapColor(MapColor.METAL)
                .sound(SoundType.METAL));

        final StateDefinition.Builder<Block, BlockState> stateDefinitionBuilder = new StateDefinition.Builder<>(this);
        this.createBlockStateDefinition(stateDefinitionBuilder);
        this.creativeTankStateDefinition = stateDefinitionBuilder.create(Block::defaultBlockState, State::new);

        registerDefaultState(getStateDefinition().any());
    }

    protected StateDefinition<Block, BlockState> creativeTankStateDefinition;
    @Override
    public @NotNull StateDefinition<Block, BlockState> getStateDefinition() {
        return this.creativeTankStateDefinition;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return BlocksRegistry.CREATIVE_TANK_BLOCK_ENTITY.get().create(pos, state);
    }

    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        if(level.isClientSide()) return null;
        return blockEntityType == BlocksRegistry.CREATIVE_TANK_BLOCK_ENTITY.get() ? CreativeTankBlockEntity::ticker : null;
    }

    public static class State extends BlockState {
        public State(Block block, ImmutableMap<Property<?>, Comparable<?>> map, MapCodec<BlockState> codec) {
            super(block, map, codec);
        }

        @Override
        public @NotNull InteractionResult use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
            if (level.isClientSide())
                return InteractionResult.SUCCESS;

            if (!(level.getBlockEntity(hit.getBlockPos()) instanceof CreativeTankBlockEntity createTankBlockEntity))
                throw new IllegalStateException("BlockEntity is missing");

            ItemStack heldItem = player.getItemInHand(hand);
            if (FluidUtil.getFluidHandler(heldItem).isPresent()) {
                FluidStack stack = FluidUtil.getFluidHandler(heldItem).map(h -> h.getFluidInTank(0)).orElse(FluidStack.EMPTY);
                createTankBlockEntity.emptyIfDifferentFluidStack(stack);
                if (FluidUtil.interactWithFluidHandler(player, hand, level, hit.getBlockPos(), hit.getDirection())) {
                    createTankBlockEntity.setMaxCapacity();
                    return InteractionResult.SUCCESS;
                } else {
                    return InteractionResult.FAIL;
                }
            }

            return InteractionResult.SUCCESS;
        }
    }
}