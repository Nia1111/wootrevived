package wootrevived.woot.util.factory;

import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.*;

public class WootConcatFluidStack {
    final Fluid fluid;
    final PatchedDataComponentMap components;

    public WootConcatFluidStack(Fluid fluid, PatchedDataComponentMap components) {
        this.fluid = fluid;
        this.components = components;
    }

    @Override
    public int hashCode(){
        return Objects.hash(fluid, components);
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof WootConcatFluidStack stack)) return false;
        return fluid == stack.fluid && Objects.equals(components, stack.components);
    }

    public static List<FluidStack> merge(List<FluidStack> unconcatFluids){
        Map<WootConcatFluidStack, FluidStack> map = new HashMap<>();
        for(FluidStack fluidStack : unconcatFluids){
            if(fluidStack.isEmpty()) continue;
            WootConcatFluidStack stack = new WootConcatFluidStack(fluidStack.getFluid(), fluidStack.getComponents());
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
