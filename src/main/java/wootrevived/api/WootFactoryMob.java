package wootrevived.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import wootrevived.api.enums.Tier;
import wootrevived.api.interfaces.WootDropsProperties;

import java.util.List;

public class WootFactoryMob<T extends Entity> {
    private final Properties properties;
    protected final EntityType<T> entityType;

    public WootFactoryMob(EntityType<T> entityType, Properties properties) {
        this.entityType = entityType;
        this.properties = properties;

        if(properties.vitalityCost == -1)
            properties.vitalityCost = properties.tier.defaultVitalityCost();
    }

    public MutableComponent getDisplayName(CompoundTag mobTag) {
        return Component.translatable(entityType.getDescriptionId());
    }

    public MutableComponent getTooltipKillName(CompoundTag mobTag) {
        return getDisplayName(mobTag);
    }

    public CompoundTag saveTag(CompoundTag mobTag){
        CompoundTag tag = new CompoundTag();
        tag.putString("id", mobTag.getString("id"));
        return tag;
    }

    public boolean isSame(CompoundTag shardTag, CompoundTag mobTag){
        return shardTag.getString("id").equals(mobTag.getString("id"));
    }

    public void modifyDrops(Phase phase, WootDropsProperties properties) {
    }

    public List<ItemStack> getImportItems(CompoundTag mobTag){
        return List.of();
    }

    public List<FluidStack> getImportFluids(CompoundTag mobTag){
        return List.of();
    }

    public final EntityType<T> getEntityType() {
        return entityType;
    }

    public enum Phase {
        BEFORE_DROP_CALLBACKS,
        AFTER_DROP_CALLBACKS,
        AFTER_UPGRADES;

        @SuppressWarnings("BooleanMethodIsAlwaysInverted")
        public boolean isBeforeDropCallback(){
            return this == BEFORE_DROP_CALLBACKS;
        }

        @SuppressWarnings("BooleanMethodIsAlwaysInverted")
        public boolean isAfterDropCallback(){
            return this == AFTER_DROP_CALLBACKS;
        }

        @SuppressWarnings("BooleanMethodIsAlwaysInverted")
        public boolean isAfterUpgrades(){
            return this == AFTER_UPGRADES;
        }
    }

    public static final class Properties {
        boolean blacklist = false;
        boolean disabledSimulation = false;
        boolean showTooltipNBT = false; // Add (+NBT) to the Mob Shard & Controller entity name tooltip
        boolean importItem = false;
        boolean importFluid = false;
        int rate = 400; // 20 sec
        int vitalityCost = -1; // mB
        Tier tier = Tier.TIER_1;

        public Properties blacklist(boolean isBlacklisted) {
            this.blacklist = isBlacklisted;
            return this;
        }

        public Properties disabledSimulation(boolean isSimulationDisabled) {
            this.disabledSimulation = isSimulationDisabled;
            return this;
        }

        public Properties showTooltipNBT(boolean showTooltipNBT) {
            this.showTooltipNBT = showTooltipNBT;
            return this;
        }

        public Properties importItem(boolean needItemImport){
            this.importItem = needItemImport;
            return this;
        }

        public Properties importFluid(boolean needFluidImport){
            this.importFluid = needFluidImport;
            return this;
        }

        public Properties rate(int spawnTickRate){
            this.rate = Math.max(0, spawnTickRate);
            return this;
        }

        public Properties vitalityCost(int vitalityCost){
            this.vitalityCost = Math.max(0, vitalityCost);
            return this;
        }

        public Properties tier(Tier tier) {
            if(tier == Tier.INVALID) return this;
            this.tier = tier;
            return this;
        }
    }

    public final boolean isBlacklisted(){
        return this.properties.blacklist;
    }

    public final boolean isSimulationDisabled(){
        return this.properties.disabledSimulation;
    }

    public final boolean showTooltipNBT(){
        return this.properties.showTooltipNBT;
    }

    public final boolean needItemImport(){
        return this.properties.importItem;
    }

    public final boolean needFluidImport(){
        return this.properties.importFluid;
    }

    public final int getSpawnTickRate(){
        return this.properties.rate;
    }

    public final int getVitalityFuelCost(){
        return this.properties.vitalityCost;
    }

    public final Tier getTier(){
        return this.properties.tier;
    }
}
