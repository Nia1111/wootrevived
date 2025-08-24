package wootrevived.api.registrations;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import wootrevived.api.WootUpgradeItem;

/**
 * Registration helper for adding custom {@link WootUpgradeItem upgrade items}.
 * <p>
 * Instances of this class are passed into {@code IWootPlugin.registerUpgradeItems}
 * and should be used by plugin authors to integrate new upgrades into Woot.
 */
public abstract class WootUpgradeItemRegistration {
    /**
     * Registers a new {@link WootUpgradeItem}.
     *
     * @param item a {@link RegistryObject} referring to your upgrade item
     */
    public abstract void register(RegistryObject<? extends WootUpgradeItem> item);

    /**
     * Provides access to Woot's internal event bus.
     * <p>
     * This allows external mods to register their own upgrade items in the same
     * way Woot Revived does internally. Intended for mods that extend or
     * complement Woot with additional upgrade items.
     *
     * @return the Woot event bus
     */
    public abstract IEventBus getWootEventBus();
}
