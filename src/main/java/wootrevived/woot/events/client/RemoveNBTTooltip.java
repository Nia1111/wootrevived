package wootrevived.woot.events.client;

import net.minecraft.network.chat.FormattedText;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import wootrevived.woot.Woot;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Woot.MOD_ID, value = { Dist.CLIENT })
public class RemoveNBTTooltip {
    @SubscribeEvent
    public static void onGatherTooltip(RenderTooltipEvent.GatherComponents event) {
        String namespace = ForgeRegistries.ITEMS.getKey(event.getItemStack().getItem()).getNamespace();
        if(namespace.equals("woot_revived")){
            event.getTooltipElements().removeIf(either -> either.left().map(FormattedText::getString).orElse("").equals("(+NBT)"));
        }
    }
}
