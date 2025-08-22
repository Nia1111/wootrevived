package wootrevived.woot.drops.mobs;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import wootrevived.api.WootFactoryMob;
import wootrevived.api.enums.Tier;
import wootrevived.api.registrations.WootFactoryMobRegistration;

public class CreeperMob extends WootFactoryMob<Creeper> {
    public CreeperMob(EntityType<Creeper> entityType, Properties properties) {
        super(entityType, properties);
    }

    @Override
    public MutableComponent getDisplayName(CompoundTag mobTag) {
        MutableComponent tip = Component.empty();
        if(mobTag.contains("powered") && mobTag.getBoolean("powered")){
            tip.append("Charged ");
        }
        return tip.append(super.getDisplayName(mobTag));
    }

    @Override
    public CompoundTag saveTag(CompoundTag mobTag){
        CompoundTag tag = super.saveTag(mobTag);
        if(mobTag.contains("powered"))
            tag.putBoolean("powered", mobTag.getBoolean("powered"));
        return tag;
    }

    @Override
    public boolean isSame(CompoundTag shardTag, CompoundTag mobTag){
        if(!super.isSame(shardTag, mobTag))
            return false;

        boolean isShardPowered = shardTag.contains("powered") && shardTag.getBoolean("powered");
        boolean isMobPowered = mobTag.contains("powered") && mobTag.getBoolean("powered");
        return isShardPowered == isMobPowered;
    }

    public static void register(WootFactoryMobRegistration registration) {
        registration.registerFactoryMob(new CreeperMob(EntityType.CREEPER, new Properties().tier(Tier.TIER_2)));
    }
}
