package wootrevived.woot.events;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import wootrevived.woot.Woot;
import wootrevived.woot.network.WootMachineUpdate;

@EventBusSubscriber(modid = Woot.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class RegisterPayloadHandlers {
    @SubscribeEvent
    public static void registerPayloadHandler(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(Woot.MOD_ID).versioned("1").optional();

        registrar.playToServer(
                WootMachineUpdate.TYPE,
                WootMachineUpdate.STREAM_CODEC,
                WootMachineUpdate::handler
        );
    }
}
