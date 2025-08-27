package wootrevived.woot.blocks.fluid_infuser;

import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import org.jetbrains.annotations.NotNull;
import wootrevived.woot.data.FluidInfuserData;
import wootrevived.woot.registries.BlocksRegistry;
import wootrevived.woot.registries.ComponentsRegistry;
import wootrevived.woot.util.Config;

import org.jetbrains.annotations.Nullable;
import wootrevived.woot.util.render.WootContainerScreen;

import java.util.List;

import static wootrevived.woot.util.render.WootStyles.MACHINE_STYLE;
import static wootrevived.woot.util.render.WootStyles.UNIT_STYLE;

public class FluidInfuserBlock extends Block implements EntityBlock {
    public FluidInfuserBlock() {
        super(Properties.of()
                .mapColor(MapColor.METAL)
                .sound(SoundType.METAL)
                .strength(3.5F));

        final StateDefinition.Builder<Block, BlockState> stateDefinitionBuilder = new StateDefinition.Builder<>(this);
        this.createBlockStateDefinition(stateDefinitionBuilder);
        this.fluidConvertorStateDefinition = stateDefinitionBuilder.create(Block::defaultBlockState, State::new);

        registerDefaultState(getStateDefinition().any().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));
    }

    protected StateDefinition<Block, BlockState> fluidConvertorStateDefinition;
    @Override
    public @NotNull StateDefinition<Block, BlockState> getStateDefinition() {
        return this.fluidConvertorStateDefinition;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return BlocksRegistry.FLUID_INFUSER_BLOCK_ENTITY.get().create(pos, state);
    }

    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        if(level.isClientSide()) return null;
        return blockEntityType == BlocksRegistry.FLUID_INFUSER_BLOCK_ENTITY.get() ? FluidInfuserBlockEntity::ticker : null;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext ctx, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, ctx, tooltip, flag);

        FluidInfuserData.Component component = stack.get(ComponentsRegistry.FLUID_INFUSER_DATA);
        if(component == null)
            return;

        tooltip.add(
                Component.empty()
                        .append(Component.translatable("info.woot_revived.power").append(Component.literal(": ")).setStyle(MACHINE_STYLE))
                        .append(Component.literal(WootContainerScreen.formatInteger(component.energy())))
                        .append(Component.literal("/").setStyle(MACHINE_STYLE))
                        .append(WootContainerScreen.formatInteger(Config.FluidInfuser.ENERGY_CAPACITY))
                        .append(Component.literal(" FE").setStyle(UNIT_STYLE))
        );

        FluidStack inputFluid = component.inputFluid();

        tooltip.add(
                Component.empty()
                        .append(Component.translatable("info.woot_revived.input_fluid").append(Component.literal(": ")).setStyle(MACHINE_STYLE))
                        .append(!inputFluid.isEmpty() ? inputFluid.getHoverName() : Component.translatable("info.woot_revived.empty"))
        );

        tooltip.add(
                Component.empty()
                        .append(Component.translatable("info.woot_revived.input_amount").append(Component.literal(": ")).setStyle(MACHINE_STYLE))
                        .append(WootContainerScreen.formatInteger(inputFluid.getAmount()))
                        .append(Component.literal("/").setStyle(MACHINE_STYLE))
                        .append(WootContainerScreen.formatInteger(Config.FluidInfuser.INPUT_TANK_CAPACITY))
                        .append(Component.literal("mB").setStyle(UNIT_STYLE))
        );

        FluidStack outputFluid = component.outputFluid();

        tooltip.add(
                Component.empty()
                        .append(Component.translatable("info.woot_revived.output_fluid").append(Component.literal(": ")).setStyle(MACHINE_STYLE))
                        .append(!outputFluid.isEmpty() ? outputFluid.getHoverName() : Component.translatable("info.woot_revived.empty"))
        );

        tooltip.add(
                Component.empty()
                        .append(Component.translatable("info.woot_revived.output_amount").append(Component.literal(": ")).setStyle(MACHINE_STYLE))
                        .append(WootContainerScreen.formatInteger(outputFluid.getAmount()))
                        .append(Component.literal("/").setStyle(MACHINE_STYLE))
                        .append(WootContainerScreen.formatInteger(Config.FluidInfuser.OUTPUT_TANK_CAPACITY))
                        .append(Component.literal("mB").setStyle(UNIT_STYLE))
        );
    }

    public static class State extends BlockState {
        public State(Block block, Reference2ObjectArrayMap<Property<?>, Comparable<?>> map, MapCodec<BlockState> codec) {
            super(block, map, codec);
        }

        @Override
        public @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack heldItem, @NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
            if (level.isClientSide)
                return ItemInteractionResult.SUCCESS;

            if (FluidUtil.getFluidHandler(heldItem).isPresent())
                return FluidUtil.interactWithFluidHandler(player, hand, level, hit.getBlockPos(), hit.getDirection()) ? ItemInteractionResult.SUCCESS : ItemInteractionResult.FAIL;

            if (!(level.getBlockEntity(hit.getBlockPos()) instanceof FluidInfuserBlockEntity blockEntity))
                throw new IllegalStateException("BlockEntity is missing");

            player.openMenu(blockEntity, buf -> buf.writeBlockPos(hit.getBlockPos()));

            return ItemInteractionResult.SUCCESS;
        }

        @Override
        public @NotNull InteractionResult useWithoutItem(@NotNull Level level, @NotNull Player player, @NotNull BlockHitResult hit){
            return useItemOn(ItemStack.EMPTY, level, player, InteractionHand.MAIN_HAND, hit).result();
        }

        @Override
        public void onRemove(@NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean isMoving) {
            if (getBlock() != newState.getBlock()) {
                BlockEntity be = level.getBlockEntity(pos);
                if (be instanceof FluidInfuserBlockEntity fluidInfuserBlockEntity)
                    fluidInfuserBlockEntity.dropContents(level, pos);
                super.onRemove(level, pos, newState, isMoving);
            }
        }

        public BlockState rotate(LevelAccessor level, BlockPos pos, Rotation rotation) {
            return setValue(BlockStateProperties.HORIZONTAL_FACING, rotation.rotate(getValue(BlockStateProperties.HORIZONTAL_FACING)));
        }

        @Override
        public @NotNull BlockState mirror(Mirror mirror) {
            return rotate(null, null, mirror.getRotation(getValue(BlockStateProperties.HORIZONTAL_FACING)));
        }
    }
}