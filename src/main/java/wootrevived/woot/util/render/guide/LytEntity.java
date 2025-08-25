package wootrevived.woot.util.render.guide;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Axis;
import guideme.document.LytRect;
import guideme.document.block.LytBlock;
import guideme.document.interaction.GuideTooltip;
import guideme.document.interaction.InteractiveElement;
import guideme.layout.LayoutContext;
import guideme.render.RenderContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import wootrevived.api.WootFactoryMob;
import wootrevived.woot.events.client.GlobalClientTicker;

import java.util.Optional;

public class LytEntity extends LytBlock implements InteractiveElement {
    private static double BOX_SIZE = 36D;
    private static double PADDING = 3D;

    private static float ENTITY_BOX_SIZE = 20F;

    private final WootFactoryMob<?> mob;
    private final LivingEntity entity;

    public LytEntity(WootFactoryMob<?> mob, LivingEntity entity) {
        this.mob = mob;
        this.entity = entity;
    }

    @Override
    protected LytRect computeLayout(LayoutContext context, int x, int y, int availableWidth) {
        return new LytRect(x, y, 42, 42);
    }

    @Override
    protected void onLayoutMoved(int deltaX, int deltaY) {
    }

    @Override
    public void renderBatch(RenderContext renderContext, MultiBufferSource multiBufferSource) {
    }

    @Override
    public void render(RenderContext renderContext) {
        renderContext.renderPanel(bounds);

        GuiGraphics gui = renderContext.guiGraphics();

        EntityRenderer<? super Entity> renderer = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity);

        PoseStack pose = gui.pose();
        pose.pushPose();
        pose.translate(bounds.x(), bounds.y(), 0);

        Matrix4f matrix = pose.last().pose();

        Vector3f poseScale = new Vector3f();
        matrix.getScale(poseScale);

        float width = ENTITY_BOX_SIZE / entity.getBbWidth();
        float height = ENTITY_BOX_SIZE / entity.getBbHeight();
        float scale = Math.min(width, height);
        scale = Math.min(scale, ENTITY_BOX_SIZE);

        pose.translate(BOX_SIZE / 2 + PADDING, (BOX_SIZE / 2) - PADDING + entity.getBbHeight() * scale, 64);
        pose.mulPose(Axis.YP.rotationDegrees((GlobalClientTicker.tickCounter * 4) % 360));
        pose.mulPose(Axis.ZP.rotationDegrees(180));
        pose.scale(scale, scale, scale);

        Window window = Minecraft.getInstance().getWindow();
        double windowScale = (double) window.getWidth() / (double) window.getGuiScaledWidth();

        Vector3f pos = new Vector3f();
        matrix.transformPosition(pos);

        RenderSystem.enableScissor(
                (int)((pos.x - (BOX_SIZE / 2) * poseScale.x) * windowScale),
                (int)(window.getHeight() - (pos.y + PADDING * poseScale.y) * windowScale),
                (int)Math.ceil(BOX_SIZE * windowScale * poseScale.x),
                (int)Math.ceil(BOX_SIZE * windowScale * poseScale.y)
        );
        Tesselator tesselator = Tesselator.getInstance();
        MultiBufferSource.BufferSource bufferSource = MultiBufferSource.immediate(tesselator.getBuilder());
        Lighting.setupForFlatItems();

        renderer.render(entity, entity.getYRot(), 0, pose, bufferSource, LightTexture.pack(15, 15));
        bufferSource.endLastBatch();

        RenderSystem.disableScissor();
        Lighting.setupFor3DItems();

        pose.popPose();
    }

    @Override
    public Optional<GuideTooltip> getTooltip(float x, float y) {
        return Optional.of(new EntityTooltip(mob, entity.serializeNBT()));
    }
}
