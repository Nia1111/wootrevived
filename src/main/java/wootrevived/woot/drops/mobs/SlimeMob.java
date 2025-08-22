package wootrevived.woot.drops.mobs;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Slime;
import wootrevived.api.WootFactoryMob;
import wootrevived.api.enums.Tier;
import wootrevived.api.registrations.WootFactoryMobRegistration;

public class SlimeMob extends WootFactoryMob<Slime> {
    public SlimeMob(EntityType<Slime> entityType, Properties properties) {
        super(entityType, properties);
    }

    @Override
    public MutableComponent getDisplayName(CompoundTag mobTag) {
        MutableComponent tip = Component.literal(
                mobTag.getInt("Size") > 0 ?
                        "Large " :
                        "Small "
        );
        return tip.append(super.getDisplayName(mobTag));
    }

    @Override
    public CompoundTag saveTag(CompoundTag mobTag){
        CompoundTag tag = super.saveTag(mobTag);
        tag.putInt("Size", mobTag.getInt("Size"));
        return tag;
    }

    @Override
    public boolean isSame(CompoundTag shardTag, CompoundTag mobTag){
        if(!super.isSame(shardTag, mobTag))
            return false;

        boolean isShardLarge = shardTag.getInt("Size") > 0;
        boolean isMobLarge = mobTag.getInt("Size") > 0;
        return isShardLarge == isMobLarge;
    }

    public static void register(WootFactoryMobRegistration registration) {
        registration.registerFactoryMob(new SlimeMob(EntityType.SLIME, new Properties().tier(Tier.TIER_2)));
    }
}
