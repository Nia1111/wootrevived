package wootrevived.woot.util.handlers;

import net.minecraft.nbt.Tag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.energy.EnergyStorage;

public class WootEnergyStorage extends EnergyStorage implements INBTSerializable<Tag> {
    public WootEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    protected void onEnergyChanged() {}

    public void setEnergy(int energy) {
        this.energy = energy;
        onEnergyChanged();
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int res = super.receiveEnergy(maxReceive, simulate);
        onEnergyChanged();
        return res;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int res = super.extractEnergy(maxExtract, simulate);
        onEnergyChanged();
        return res;
    }
}
