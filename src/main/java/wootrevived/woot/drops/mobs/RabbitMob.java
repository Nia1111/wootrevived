package wootrevived.woot.drops.mobs;

import com.google.common.base.CaseFormat;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Rabbit;
import wootrevived.api.WootFactoryMob;
import wootrevived.api.registrations.WootFactoryMobRegistration;

public class RabbitMob extends WootFactoryMob<Rabbit> {
    public RabbitMob(EntityType<Rabbit> entityType, Properties properties) {
        super(entityType, properties);
    }

    @Override
    public MutableComponent getDisplayName(CompoundTag mobTag) {
        Rabbit.Variant variant = Rabbit.Variant.byId(mobTag.getInt("RabbitType"));
        MutableComponent tip = Component.literal(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, variant.getSerializedName()).replaceAll("([a-z])([A-Z])", "$1 $2") + " ");
        return tip.append(super.getDisplayName(mobTag));
    }

    @Override
    public MutableComponent getTooltipKillName(CompoundTag mobTag) {
        return super.getDisplayName(mobTag);
    }

    @Override
    public CompoundTag saveTag(CompoundTag mobTag){
        CompoundTag tag = super.saveTag(mobTag);
        tag.putInt("RabbitType", mobTag.getInt("RabbitType"));
        return tag;
    }

    public static void register(WootFactoryMobRegistration registration) {
        registration.registerFactoryMob(new RabbitMob(EntityType.RABBIT, new Properties()));
    }
}
