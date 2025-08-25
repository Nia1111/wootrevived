package wootrevived.woot.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface LivingEntityMixin {
    @Invoker("dropFromLootTable")
    void invokeDropFromLootTable(DamageSource damageSource, boolean hitByPlayer);

    @Invoker("dropCustomDeathLoot")
    void invokeDropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit);

    @Invoker("dropEquipment")
    void invokeDropEquipment();

    @Invoker("dropExperience")
    void invokeDropExperience();
}
