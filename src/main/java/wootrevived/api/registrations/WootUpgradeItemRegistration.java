package wootrevived.api.registrations;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import wootrevived.api.WootUpgradeItem;

public abstract class WootUpgradeItemRegistration {
    /**
     * Registers an {@link WootUpgradeItem upgrade item}.
     */
    public abstract void register(RegistryObject<? extends WootUpgradeItem> item);

    public abstract IEventBus getWootEventBus();
}
