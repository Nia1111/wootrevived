package wootrevived.woot.compat.kubejs;

import dev.latvian.mods.kubejs.event.*;
import dev.latvian.mods.kubejs.script.ScriptType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import wootrevived.api.interfaces.WootDropsProperties;
import wootrevived.api.registrations.WootFactoryMobRegistration;
import wootrevived.woot.compat.kubejs.mobs.WootDropsPropertiesJS;
import wootrevived.woot.compat.kubejs.mobs.WootFactoryMobEventJS;

public interface WootStartupEvents {
    EventGroup GROUP = EventGroup.of("WootStartupEvents");

    private static boolean validateMob(Object o) {
        try {
            if(!(o instanceof ResourceLocation location))
                return false;
            EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(location);
            return entityType != null;
        } catch (Exception ex) {
            return false;
        }
    }

    TargetedEventHandler<ResourceLocation> MOBS = GROUP.startup("registerFactoryMob", () -> WootFactoryMobEventJS.class).requiredTarget(EventTargetType.ID.validator(WootStartupEvents::validateMob));
    EventHandler DROPS = GROUP.startup("registerGlobalDropsModifier", () -> WootDropsPropertiesJS.class);

    static void postFactoryMobs(WootFactoryMobRegistration registration) {
        registration.registerGlobalDropsModifier(WootStartupEvents::postGlobalDropsModifier);

        MOBS.forEachListener(ScriptType.STARTUP, handler -> {
            if(handler.target == null)
                throw new IllegalArgumentException("Event handler '" + MOBS + "' requires extra id!");
            ResourceLocation location = (ResourceLocation) handler.target;
            EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(location);
            WootFactoryMobEventJS event = new WootFactoryMobEventJS(registration, entityType);
            try {
                handler.handle(ScriptType.STARTUP.console, MOBS, event);
            } catch (EventExit ignored) {
            } catch (Throwable error) {
                ScriptType.STARTUP.console.error("Internal Error in '" + MOBS + "'", error);
            }
        });
    }

    static void postGlobalDropsModifier(WootDropsProperties properties){
        DROPS.post(new WootDropsPropertiesJS(properties));
    }
}
