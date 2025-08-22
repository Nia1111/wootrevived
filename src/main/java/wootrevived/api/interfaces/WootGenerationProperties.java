package wootrevived.api.interfaces;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;
import wootrevived.api.WootFactoryMob;
import wootrevived.api.enums.Tier;

public interface WootGenerationProperties {
    int getSpawnRate();
    void setSpawnRate(int spawnRate);

    int getVitalityFuelCost();
    void setVitalityFuelCost(int vitalityFuelCost);

    int getNumberOfSimulations();
    void setNumberOfSimulations(int numberOfSimulations);

    @NotNull RandomSource getRandom();
    @NotNull Tier getFactoryTier();
    @NotNull WootFactoryMob<?> getFactoryMob();
    @NotNull CompoundTag getFactoryMobTag();
}
