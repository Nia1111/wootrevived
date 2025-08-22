package wootrevived.woot.events;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import wootrevived.woot.Woot;
import wootrevived.woot.network.NetworkChannel;
import wootrevived.woot.registries.WootFactoryMobsRegistry;

@Mod.EventBusSubscriber(modid = Woot.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class InitCommon {
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event){
        NetworkChannel.init();
        WootFactoryMobsRegistry.register();
    }
}
