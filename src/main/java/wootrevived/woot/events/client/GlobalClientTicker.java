package wootrevived.woot.events.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import wootrevived.woot.Woot;

@Mod.EventBusSubscriber(modid = Woot.MOD_ID, value = { Dist.CLIENT })
public class GlobalClientTicker {
    public static int tickCounter = 0;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            tickCounter++;
        }
    }
}
