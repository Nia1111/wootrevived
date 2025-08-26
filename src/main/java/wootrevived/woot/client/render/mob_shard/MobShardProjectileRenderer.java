package wootrevived.woot.client.render.mob_shard;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import wootrevived.woot.items.mob_shard.MobShardProjectile;

@OnlyIn(Dist.CLIENT)
public class MobShardProjectileRenderer extends EntityRenderer<MobShardProjectile> {
    private final ItemRenderer itemRenderer;
    private final float scale;
    private final boolean fullBright;

    public MobShardProjectileRenderer(EntityRendererProvider.Context context, float scale, boolean fullBright){
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.scale = scale;
        this.fullBright = fullBright;
    }

    public MobShardProjectileRenderer(EntityRendererProvider.Context context) { this(context, 1f, false); }

    protected int getBlockLightLevel(@NotNull MobShardProjectile entity, @NotNull BlockPos pos) {
        return this.fullBright ? 15 : super.getBlockLightLevel(entity, pos);
    }

    public void render(MobShardProjectile entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight)
    {
        if (entity.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(entity) < 12.25D)) {
            poseStack.pushPose();
            poseStack.scale(this.scale, this.scale, this.scale);

            poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
            poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));

            Vec3 motion = entity.getDeltaMovement();

            float pull = (float) motion.length() / 2f;
            float rollAngle = (entity.tickCount + partialTicks) * (20.0f + pull * 25.0f);
            poseStack.mulPose(Axis.XP.rotationDegrees(rollAngle));

            poseStack.translate(0, 0.1, 0);

            poseStack.mulPose(Axis.ZP.rotationDegrees(225.0f));

            this.itemRenderer.renderStatic(entity.getItem(), ItemDisplayContext.GROUND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, entity.level(), entity.getId());

            poseStack.popPose();
            super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        }
    }

    public @NotNull ResourceLocation getTextureLocation(@NotNull MobShardProjectile entity) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
