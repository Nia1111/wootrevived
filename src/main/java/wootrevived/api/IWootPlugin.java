package wootrevived.api;

import wootrevived.api.registrations.WootFactoryMobRegistration;
import wootrevived.api.registrations.WootUpgradeItemRegistration;

public interface IWootPlugin {
    void registerUpgradeItems(WootUpgradeItemRegistration registration);

    void registerFactoryMobs(WootFactoryMobRegistration registration);
}
