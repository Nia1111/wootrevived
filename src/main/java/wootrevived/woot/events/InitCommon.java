package wootrevived.woot.events;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import wootrevived.woot.Woot;
import wootrevived.woot.registries.WootFactoryMobsRegistry;

@Mod.EventBusSubscriber(modid = Woot.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class InitCommon {
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event){
        WootFactoryMobsRegistry.register();
    }
}
