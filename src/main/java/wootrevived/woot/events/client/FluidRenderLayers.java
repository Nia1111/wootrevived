package wootrevived.woot.events.client;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import wootrevived.woot.Woot;
import wootrevived.woot.registries.FluidsRegistry;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Woot.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = { Dist.CLIENT })
public class FluidRenderLayers {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event){
        ItemBlockRenderTypes.setRenderLayer(FluidsRegistry.SOURCE_VITALITY_FUEL_FLUID.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidsRegistry.FLOWING_VITALITY_FUEL_FLUID.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(FluidsRegistry.SOURCE_PURE_DYE_FLUID.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidsRegistry.FLOWING_PURE_DYE_FLUID.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(FluidsRegistry.SOURCE_ENCHANTED_FLUID.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidsRegistry.FLOWING_ENCHANTED_FLUID.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(FluidsRegistry.SOURCE_MOB_TEARS_FLUID.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidsRegistry.FLOWING_MOB_TEARS_FLUID.get(), RenderType.translucent());
    }
}
