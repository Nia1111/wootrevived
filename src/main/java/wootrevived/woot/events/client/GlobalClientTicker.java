package wootrevived.woot.events.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.TickEvent;
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
