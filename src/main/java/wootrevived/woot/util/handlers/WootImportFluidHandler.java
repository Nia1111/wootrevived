package wootrevived.woot.util.handlers;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class WootImportFluidHandler implements IFluidHandler {
    private final Map<Integer, List<FluidStack>> importFluids = new HashMap<>();
    private final Map<Integer, List<Integer>> tanks = new HashMap<>();

    public void setImportFluid(int index, List<FluidStack> importFluid){
        if(isEqual(importFluids.get(index), importFluid)) return;
        importFluids.put(index, importFluid);
        if(importFluid != null) tanks.put(index, new ArrayList<>(Collections.nCopies(importFluid.size(), 0)));
    }

    private boolean isEqual(List<FluidStack> list1, List<FluidStack> list2){
        if(list1 == null && list2 == null) return true;
        if(list1 == null || list2 == null) return false;
        if(list1.size() != list2.size()) return false;

        for(int i = 0; i < list1.size(); i++){
            FluidStack fluid1 = list1.get(i);
            FluidStack fluid2 = list2.get(i);

            if(fluid1.getAmount() != fluid2.getAmount()) return false;
            if(!fluid1.isFluidEqual(fluid2)) return false;
        }

        return true;
    }

    public boolean isImportValid(int index){
        List<FluidStack> list = importFluids.get(index);
        if(list == null)
            return true;

        List<Integer> amounts = tanks.get(index);

        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getAmount() != amounts.get(i)) return false;
        }

        return true;
    }

    public void consume(int index){
        List<Integer> amounts = tanks.get(index);
        if(amounts == null)
            return;

        Collections.fill(amounts, 0);
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @Override
    public @NotNull FluidStack getFluidInTank(int tank) {
        return FluidStack.EMPTY;
    }

    @Override
    public int getTankCapacity(int tank) {
        int capacity = 0;
        for(int i = 0; i < 4; i++){
            List<FluidStack> list = importFluids.get(i);
            if(list == null)
                continue;

            for(FluidStack stack : list){
                if(capacity < stack.getAmount())
                    capacity = stack.getAmount();
            }
        }
        return capacity;
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        for(int i = 0; i < 4; i++){
            List<FluidStack> list = importFluids.get(i);
            if(list == null)
                continue;

            for(FluidStack s : list){
                if(s.isFluidEqual(stack))
                    return true;
            }
        }
        return false;
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if(!isFluidValid(0, resource) || resource.isEmpty())
            return 0;

        resource = resource.copy();
        int filled = 0;
        for(int i = 0; i < 4; i++){
            List<FluidStack> list = importFluids.get(i);
            if(list == null)
                continue;

            List<Integer> tank = tanks.get(i);

            for(int j = 0; j < list.size(); j++){
                FluidStack stack = list.get(j);
                if(stack.isFluidEqual(resource)){
                    int amount = tank.get(j);
                    int needToBeFill = stack.getAmount() - amount;
                    if(resource.getAmount() <= needToBeFill){
                        filled += resource.getAmount();
                        if(!action.simulate()) tank.set(j, amount + resource.getAmount());
                        return filled;
                    } else {
                        filled += needToBeFill;
                        if(!action.simulate()) tank.set(j, amount + needToBeFill);
                        resource.shrink(needToBeFill);
                    }
                }
            }
        }
        return filled;
    }

    @Override
    public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {
        return FluidStack.EMPTY;
    }

    @Override
    public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
        return FluidStack.EMPTY;
    }

    public void save(CompoundTag tag){
        ListTag list = new ListTag();
        for(int i = 0; i < 4; i++){
            CompoundTag compoundTag = new CompoundTag();
            List<FluidStack> stackList = importFluids.get(i);
            compoundTag.putBoolean("IsNull", stackList == null);
            if(stackList == null) {
                list.add(compoundTag);
                continue;
            }

            ListTag stackListTag = new ListTag();
            for(int j = 0; j < stackList.size(); j++){
                CompoundTag stackTag = new CompoundTag();
                FluidStack fluidStack = stackList.get(j);
                CompoundTag fluidTag = new CompoundTag();
                fluidStack.writeToNBT(fluidTag);
                stackTag.put("Fluid", fluidTag);
                stackTag.putInt("Amount", tanks.get(i).get(j));
                stackListTag.add(stackTag);
            }

            compoundTag.put("Stacks", stackListTag);
            list.add(compoundTag);
        }
        tag.put("FluidHandler",  list);
    }

    public void load(CompoundTag tag){
        importFluids.clear();
        tanks.clear();

        ListTag list = tag.getList("FluidHandler", Tag.TAG_COMPOUND);
        for(int i = 0; i < 4; i++){
            CompoundTag compoundTag = list.getCompound(i);
            if(compoundTag.getBoolean("IsNull"))
                continue;

            ListTag stackListTag = compoundTag.getList("Stacks", Tag.TAG_COMPOUND);
            List<FluidStack> stackList = new ArrayList<>();
            List<Integer> tanks = new ArrayList<>();
            for(int j = 0; j < stackListTag.size(); j++){
                CompoundTag stackTag = stackListTag.getCompound(j);
                CompoundTag fluid = stackTag.getCompound("Fluid");
                stackList.add(FluidStack.loadFluidStackFromNBT(fluid));
                tanks.add(stackTag.getInt("Amount"));
            }
            importFluids.put(i, stackList);
            this.tanks.put(i, tanks);
        }
    }
}
