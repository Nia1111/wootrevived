package wootrevived.woot.util.handlers;

import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

import java.util.function.Predicate;

public class WootFluidTankHandler extends FluidTank {
    protected final boolean isOutput;

    public WootFluidTankHandler(int capacity, boolean isOutput) {
        super(capacity);
        this.isOutput = isOutput;
    }

    public WootFluidTankHandler(int capacity, boolean isOutput, Predicate<FluidStack> validator) {
        super(capacity, validator);
        this.isOutput = isOutput;
    }

    @Override
    public FluidTank setCapacity(int capacity) {
        super.setCapacity(capacity);
        onContentsChanged();
        return this;
    }

    @Override
    public void setFluid(FluidStack stack){
        super.setFluid(stack);
        onContentsChanged();
    }

    public boolean isOutput(){
        return isOutput;
    }

    public Predicate<FluidStack> getValidator(){
        return validator;
    }
}
