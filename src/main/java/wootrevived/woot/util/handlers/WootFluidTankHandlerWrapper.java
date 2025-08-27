package wootrevived.woot.util.handlers;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import wootrevived.woot.util.common.MachineSideProperty;

import java.util.function.Supplier;

public class WootFluidTankHandlerWrapper extends WootFluidTankHandler {
    private final WootFluidTankHandler tank;
    private final Supplier<MachineSideProperty> getProperty;

    public WootFluidTankHandlerWrapper(WootFluidTankHandler tank, Supplier<MachineSideProperty> getProperty) {
        super(tank.getCapacity(), tank.isOutput(), tank.getValidator());
        this.tank = tank;
        this.getProperty = getProperty;
        this.fluid = tank.getFluid();
    }

    @Override
    public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
        MachineSideProperty property = getProperty.get();
        if(isOutput || property == MachineSideProperty.DISABLED || property == MachineSideProperty.PUSH)
            return 0;
        return tank.fill(resource, action);
    }

    @Override
    public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {
        MachineSideProperty property = getProperty.get();
        if(property == MachineSideProperty.DISABLED || property == MachineSideProperty.PULL)
            return FluidStack.EMPTY;
        return tank.drain(resource, action);
    }

    @Override
    public @NotNull FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
        MachineSideProperty property = getProperty.get();
        if(property == MachineSideProperty.DISABLED || property == MachineSideProperty.PULL)
            return FluidStack.EMPTY;
        return tank.drain(maxDrain, action);
    }

    @Override
    public FluidTank setCapacity(int capacity){
        tank.setCapacity(capacity);
        return super.setCapacity(capacity);
    }

    @Override
    public boolean isFluidValid(FluidStack stack){
        return tank.getValidator().test(stack);
    }

    @Override
    public int getCapacity()
    {
        return tank.getCapacity();
    }

    @Override
    public @NotNull FluidStack getFluid()
    {
        return tank.getFluid();
    }

    @Override
    public int getFluidAmount()
    {
        return tank.getFluid().getAmount();
    }

    @Override
    public FluidTank readFromNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt) {
        FluidStack fluid = FluidStack.parseOptional(lookupProvider, nbt.getCompound("Fluid"));
        tank.setFluid(fluid);
        super.setFluid(fluid);
        return this;
    }

    @Override
    public CompoundTag writeToNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt) {
        if (!tank.getFluid().isEmpty()) {
            nbt.put("Fluid", tank.getFluid().save(lookupProvider));
        }
        return nbt;
    }

    @Override
    public void setFluid(FluidStack stack) {
        tank.setFluid(stack);
        super.setFluid(stack);
    }

    @Override
    public boolean isEmpty()
    {
        return tank.getFluid().isEmpty();
    }

    @Override
    public int getSpace()
    {
        return tank.getSpace();
    }
}
