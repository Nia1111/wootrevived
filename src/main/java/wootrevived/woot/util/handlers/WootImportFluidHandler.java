package wootrevived.woot.util.handlers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class WootImportFluidHandler implements IFluidHandler {
    private final Map<Integer, List<FluidStack>> importFluids = new HashMap<>();
    private final Map<Integer, List<Integer>> tanks = new HashMap<>();

    private Map<Integer, List<FluidStack>> getImportFluids() {
        return importFluids;
    }

    private Map<Integer, List<Integer>> getInternalTanks(){
        return tanks;
    }

    public static final Codec<WootImportFluidHandler> CODEC = RecordCodecBuilder.create(inst ->
            inst.group(
                    Codec.unboundedMap(Codec.STRING, FluidStack.OPTIONAL_CODEC.listOf()).xmap(
                            m -> m.entrySet().stream().collect(Collectors.toMap(e -> Integer.parseInt(e.getKey()), Map.Entry::getValue)),
                            m -> m.entrySet().stream().collect(Collectors.toMap(e -> Integer.toString(e.getKey()), Map.Entry::getValue))
                    ).fieldOf("ImportTanks").forGetter(WootImportFluidHandler::getImportFluids),
                    Codec.unboundedMap(Codec.STRING, Codec.INT.listOf()).xmap(
                            m -> m.entrySet().stream().collect(Collectors.toMap(e -> Integer.parseInt(e.getKey()), Map.Entry::getValue)),
                            m -> m.entrySet().stream().collect(Collectors.toMap(e -> Integer.toString(e.getKey()), Map.Entry::getValue))
                    ).fieldOf("Tanks").forGetter(WootImportFluidHandler::getInternalTanks)
            ).apply(inst, WootImportFluidHandler::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, WootImportFluidHandler> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.map(
                    HashMap::new,
                    ByteBufCodecs.INT,
                    FluidStack.OPTIONAL_STREAM_CODEC.apply(ByteBufCodecs.collection(NonNullList::createWithCapacity))
            ), WootImportFluidHandler::getImportFluids,

            ByteBufCodecs.map(
                    HashMap::new,
                    ByteBufCodecs.INT,
                    ByteBufCodecs.INT.apply(ByteBufCodecs.collection(NonNullList::createWithCapacity))
            ), WootImportFluidHandler::getInternalTanks,

            WootImportFluidHandler::new
    );

    public WootImportFluidHandler() {}

    private WootImportFluidHandler(Map<Integer, List<FluidStack>> importFluids, Map<Integer, List<Integer>> tanks) {
        this.importFluids.putAll(importFluids);
        this.tanks.putAll(tanks);
    }

    public void setImportFluid(int index, List<FluidStack> importFluid){
        if(importFluid == null){
            if(importFluids.containsKey(index)){
                importFluids.remove(index);
                tanks.remove(index);
            }
            return;
        }

        if(isEqual(importFluids.get(index), importFluid)) return;

        importFluids.put(index, importFluid);
        tanks.put(index, new ArrayList<>(Collections.nCopies(importFluid.size(), 0)));
    }

    private boolean isEqual(List<FluidStack> list1, List<FluidStack> list2){
        if(list1 == null) return false;
        if(list1.size() != list2.size()) return false;

        for(int i = 0; i < list1.size(); i++){
            FluidStack fluid1 = list1.get(i);
            FluidStack fluid2 = list2.get(i);

            if(fluid1.getAmount() != fluid2.getAmount()) return false;
            if(!FluidStack.isSameFluidSameComponents(fluid1, fluid2)) return false;
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
                if(FluidStack.isSameFluidSameComponents(s, stack))
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
                if(FluidStack.isSameFluidSameComponents(stack, resource)){
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
}
