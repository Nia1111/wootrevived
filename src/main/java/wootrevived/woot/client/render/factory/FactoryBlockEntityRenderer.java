package wootrevived.woot.client.render.factory;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.RenderTypeHelper;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import wootrevived.woot.blocks.factory_upgrade.FactoryUpgradeBlockEntity;

@OnlyIn(Dist.CLIENT)
public class FactoryBlockEntityRenderer implements BlockEntityRenderer<BlockEntity> {
    @Override
    public void render(BlockEntity blockEntity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, int packedOverlay){
        BlockState blockState = blockEntity.getBlockState();

        Level level = blockEntity.getLevel();

        if(blockState.getRenderShape() == RenderShape.MODEL){
            if(blockEntity instanceof FactoryUpgradeBlockEntity factoryUpgradeBlockEntity)
                factoryUpgradeBlockEntity.tryRequestModelDataUpdate();
            return;
        }

        BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
        BakedModel blockModel = blockRenderer.getBlockModel(blockState);
        ModelData blockModelData = blockModel.getModelData(level, blockEntity.getBlockPos(), blockState, ModelData.EMPTY);
        long seed = blockState.getSeed(blockEntity.getBlockPos());

        boolean isNonAttached = blockState.hasProperty(BlockStateProperties.ATTACHED) && !blockState.getValue(BlockStateProperties.ATTACHED);
        boolean isDisabled = blockState.hasProperty(BlockStateProperties.ENABLED) && !blockState.getValue(BlockStateProperties.ENABLED);

        float scale;
        if (isDisabled) {
            scale = 0.5f;
        } else if (isNonAttached) {
            scale = 0.75f;
        } else { // Should never happen
            scale = 1.0f;
        }

        poseStack.pushPose();

        poseStack.translate((1.0f - scale) / 2f, (1.0f - scale) / 2f, (1.0f - scale) / 2f);
        poseStack.scale(scale, scale, scale);

        for (RenderType renderType : blockModel.getRenderTypes(blockState, RandomSource.create(seed), blockModelData))
            blockRenderer.getModelRenderer().renderModel(poseStack.last(), buffer.getBuffer(RenderTypeHelper.getEntityRenderType(renderType, false)), blockState, blockModel, 0f, 0f, 0f, packedLight, packedOverlay, blockModelData, renderType);

        poseStack.popPose();
    }
}
