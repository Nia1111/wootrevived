package wootrevived.woot.events;

import net.minecraft.commands.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import wootrevived.woot.Woot;
import wootrevived.woot.commands.GiveCommand;

@Mod.EventBusSubscriber(modid = Woot.MOD_ID)
public class CommandsRegister {
    @SubscribeEvent
    public static void onRegisterCommandsEvent(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("woot")
                        .then(GiveCommand.register())
        );
    }
}
