package wootrevived.woot.util.factory;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.*;

public class WootConcatFluidStack {
    final Fluid fluid;
    final CompoundTag tag;

    public WootConcatFluidStack(Fluid fluid, CompoundTag tag) {
        this.fluid = fluid;
        this.tag = tag;
    }

    @Override
    public int hashCode(){
        return Objects.hash(fluid, tag);
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof WootConcatFluidStack stack)) return false;
        return fluid == stack.fluid && Objects.equals(tag, stack.tag);
    }

    public static List<FluidStack> merge(List<FluidStack> unconcatFluids){
        Map<WootConcatFluidStack, FluidStack> map = new HashMap<>();
        for(FluidStack fluidStack : unconcatFluids){
            if(fluidStack.isEmpty()) continue;
            WootConcatFluidStack stack = new WootConcatFluidStack(fluidStack.getFluid(), fluidStack.getTag());
            if(map.containsKey(stack)){
                FluidStack existing = map.get(stack);
                existing.grow(fluidStack.getAmount());
            } else {
                map.put(stack, fluidStack.copy());
            }
        }

        return new ArrayList<>(map.values());
    }
}
