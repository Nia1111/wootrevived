package wootrevived.api.interfaces;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import wootrevived.api.WootFactoryMob;
import wootrevived.api.enums.Tier;
import org.jetbrains.annotations.NotNull;

public interface WootSpawnProperties {
    @NotNull ItemStack getMainHandItem();
    void setMainHandItem(@NotNull ItemStack itemStack);

    @NotNull ItemStack getOffHandItem();
    void setOffHandItem(@NotNull ItemStack itemStack);

    float getLuck();
    void setLuck(float luck);

    boolean doSimulateChargedCreeper();
    void setDoSimulateChargedCreeper(boolean doSimulateChargedCreeper);

    boolean isEnderDragonAlreadyKilled();
    void setEnderDragonAlreadyKilled(boolean enderDragonAlreadyKilled);

    boolean isInFire();
    void setIsInFire(boolean isInFire);

    @NotNull RandomSource getRandom();
    @NotNull Tier getFactoryTier();
    @NotNull WootFactoryMob<?> getFactoryMob();
    @NotNull CompoundTag getFactoryMobTag();
    void setFactoryMobTag(CompoundTag tag);
}
