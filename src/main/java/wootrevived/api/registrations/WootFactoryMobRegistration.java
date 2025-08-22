package wootrevived.api.registrations;

import net.minecraft.world.entity.EntityType;
import wootrevived.api.interfaces.WootDropsProperties;
import wootrevived.api.WootFactoryMob;

import java.util.function.Consumer;

public abstract class WootFactoryMobRegistration {
    /**
     * Registers a custom {@link WootFactoryMob factory mob} behavior.
     *
     * <p>Only one instance of a {@link WootFactoryMob factory mob} can exist in the internal registry</p>
     */
    public abstract void registerFactoryMob(WootFactoryMob<?> mob);

    /**
     * Registers a function to modify the drops of an entity
     *
     * <p>Use this if you just want to add custom drops without creating a custom factory mob</p>
     */
    public abstract void registerDropsModifier(EntityType<?> entityType, Consumer<WootDropsProperties> callback);
}
