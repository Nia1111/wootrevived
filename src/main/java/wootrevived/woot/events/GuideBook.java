package wootrevived.woot.events;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import wootrevived.woot.Woot;
import wootrevived.woot.guide.GuideBookPersistentState;
import wootrevived.woot.init.CommonConfig;
import wootrevived.woot.registries.ItemsRegistry;

@Mod.EventBusSubscriber(modid = Woot.MOD_ID)
public class GuideBook {
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        MinecraftServer server = player.getServer();
        if(server == null)
            return;

        if(CommonConfig.GIVE_GUIDE_ON_SPAWN.get()){
            GuideBookPersistentState state = GuideBookPersistentState.get(player.getServer());
            if(!state.hasPlayerReceivedGuideBook(player) && player.getInventory().add(ItemsRegistry.GUIDE_BOOK_ITEM.get().getDefaultInstance())){
                state.addPlayerReceivedGuideBook(player);
            }
        }
    }
}
