package wootrevived.woot.items.mob_shard;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MobShardItemEntity extends ItemEntity {
    public MobShardItemEntity(Level level, double posX, double posY, double posZ, ItemStack itemStack)  {
        super(level, posX, posY, posZ, itemStack);
    }

    @Override
    public boolean hurt(DamageSource source, float amount){
        if(source.is(DamageTypeTags.IS_FIRE)) super.hurt(source, amount);
        return false;
    }
}
