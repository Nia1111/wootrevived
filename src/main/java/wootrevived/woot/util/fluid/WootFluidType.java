package wootrevived.woot.util.fluid;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class WootFluidType extends FluidType {
    private final ResourceLocation stillTexture;
    private final ResourceLocation flowingTexture;
    private final ResourceLocation overlayTexture;
    private final int fogColorR;
    private final int fogColorG;
    private final int fogColorB;
    private final Vector3f fogColor;

    public WootFluidType(final ResourceLocation stillTexture, final ResourceLocation flowingTexture, final ResourceLocation overlayTexture, final int fogColorR, int fogColorG, int fogColorB, final Properties properties) {
        super(properties);
        this.stillTexture = stillTexture;
        this.flowingTexture = flowingTexture;
        this.overlayTexture = overlayTexture;
        this.fogColorR = fogColorR;
        this.fogColorG = fogColorG;
        this.fogColorB = fogColorB;
        this.fogColor = new Vector3f((float)fogColorR / 255f, (float)fogColorG / 255f, (float)fogColorB / 255f);
    }

    public final IClientFluidTypeExtensions EXTENSION = new IClientFluidTypeExtensions() {
        @Override
        public ResourceLocation getStillTexture() {
            return stillTexture;
        }

        @Override
        public ResourceLocation getFlowingTexture() {
            return flowingTexture;
        }

        @Override
        public ResourceLocation getOverlayTexture() {
            return overlayTexture;
        }

        @Override
        public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor){
            return fogColor;
        }

        @Override
        public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape){
            RenderSystem.setShaderFogStart(1f);
            RenderSystem.setShaderFogEnd(6f);
        }
    };

    public ResourceLocation getStillTexture() {
        return stillTexture;
    }

    public ResourceLocation getFlowingTexture() {
        return flowingTexture;
    }

    public ResourceLocation getOverlayTexture() {
        return overlayTexture;
    }

    public int getColor(){
        return fogColorR << 16 | fogColorG << 8 | fogColorB;
    }

    public int getTintColor(){
        return getColor();
    }

    public Vector3f getFogColor(){
        return this.fogColor;
    }
}
