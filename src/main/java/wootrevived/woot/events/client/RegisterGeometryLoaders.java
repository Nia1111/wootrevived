package wootrevived.woot.events.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;
import wootrevived.woot.Woot;
import wootrevived.woot.client.model.factory_upgrade.FactoryUpgradeUnbakedModel;
import wootrevived.woot.registries.BlocksRegistry;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = Woot.MOD_ID, value = { Dist.CLIENT })
public class RegisterGeometryLoaders {
    @SubscribeEvent
    public static void registerGeometryLoaders(ModelEvent.RegisterGeometryLoaders event) {
        event.register(Woot.location(BlocksRegistry.FACTORY_UPGRADE_TAG), FactoryUpgradeUnbakedModel.Loader.INSTANCE);
    }
}
