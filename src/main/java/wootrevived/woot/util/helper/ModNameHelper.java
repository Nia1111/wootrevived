package wootrevived.woot.util.helper;

import com.google.common.base.CaseFormat;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;

import java.util.Optional;

public class ModNameHelper {
    public static MutableComponent getModName(String modId) {
        if(modId.equals("minecraft")) return Component.literal("Minecraft");
        Optional<? extends ModContainer> container = ModList.get().getModContainerById(modId);
        return container.map(modContainer -> Component.literal(modContainer.getModInfo().getDisplayName())).orElseGet(() -> Component.literal(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, modId)));
    }
}
