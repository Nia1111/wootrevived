package wootrevived.api.interfaces;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import wootrevived.api.WootFactoryMob;
import wootrevived.api.enums.Tier;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface WootDropsProperties {
    @NotNull ItemStack getMainHandItem();
    @NotNull ItemStack getOffHandItem();
    float getLuck();
    boolean doSimulateChargedCreeper();
    boolean isEnderDragonAlreadyKilled();
    boolean isInFire();

    List<ItemStack> getItemDrops();

    List<FluidStack> getFluidDrops();

    int getExperience();
    void setExperience(int experience);

    @NotNull RandomSource getRandom();
    @NotNull Tier getFactoryTier();
    @NotNull WootFactoryMob<?> getFactoryMob();
    @NotNull CompoundTag getFactoryMobTag();
}
