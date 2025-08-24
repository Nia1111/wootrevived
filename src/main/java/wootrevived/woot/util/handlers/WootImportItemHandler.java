package wootrevived.woot.util.handlers;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class WootImportItemHandler implements IItemHandler {
    private final Map<Integer, List<ItemStack>> importItems = new HashMap<>();
    private final Map<Integer, List<Integer>> items = new HashMap<>();

    public void setImportItem(int index, List<ItemStack> importItem){
        if(isEqual(importItems.get(index), importItem)) return;
        importItems.put(index, importItem);
        if(importItem != null) items.put(index, new ArrayList<>(Collections.nCopies(importItem.size(), 0)));
    }

    private boolean isEqual(List<ItemStack> list1, List<ItemStack> list2){
        if(list1 == null && list2 == null) return true;
        if(list1 == null || list2 == null) return false;
        if(list1.size() != list2.size()) return false;

        for(int i = 0; i < list1.size(); i++){
            ItemStack item1 = list1.get(i);
            ItemStack item2 = list2.get(i);

            if(item1.getCount() != item2.getCount()) return false;
            if(item1.getItem() != item2.getItem()) return false;
            if(!Objects.equals(item1.getTag(), item2.getTag())) return false;
        }

        return true;
    }

    public boolean isImportValid(int index){
        List<ItemStack> list = importItems.get(index);
        if(list == null)
            return true;

        List<Integer> counts = items.get(index);

        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getCount() != counts.get(i)) return false;
        }

        return true;
    }

    public void consume(int index){
        List<Integer> counts = items.get(index);
        if(counts == null)
            return;

        Collections.fill(counts, 0);
    }

    @Override
    public int getSlots() {
        return 1;
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        for(int i = 0; i < 4; i++){
            List<ItemStack> list = importItems.get(i);
            if(list == null)
                continue;

            for(ItemStack s : list){
                if(stack.getItem() == s.getItem() && Objects.equals(stack.getTag(), s.getTag()))
                    return true;
            }
        }
        return false;
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if(!isItemValid(0, stack) || stack.isEmpty())
            return ItemStack.EMPTY;

        stack = stack.copy();
        for(int i = 0; i < 4; i++){
            List<ItemStack> list = importItems.get(i);
            if(list == null)
                continue;

            List<Integer> item = items.get(i);

            for(int j = 0; j < list.size(); j++){
                ItemStack s = list.get(j);
                if(stack.getItem() == s.getItem() && Objects.equals(stack.getTag(), s.getTag())){
                    int amount = item.get(j);
                    int needToBeAdded = s.getCount() - amount;
                    if(stack.getCount() <= needToBeAdded){
                        if(!simulate) item.set(j, amount + stack.getCount());
                        return ItemStack.EMPTY;
                    } else {
                        if(!simulate) item.set(j, amount + needToBeAdded);
                        stack.shrink(needToBeAdded);
                    }
                }
            }
        }
        return stack;
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        return ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit(int slot) {
        int count = 0;
        for(int i = 0; i < 4; i++){
            List<ItemStack> list = importItems.get(i);
            if(list == null)
                continue;

            for(ItemStack stack : list){
                if(count < stack.getCount())
                    count = stack.getCount();
            }
        }
        return count;
    }

    public void save(CompoundTag tag){
        ListTag list = new ListTag();
        for(int i = 0; i < 4; i++){
            CompoundTag compoundTag = new CompoundTag();
            List<ItemStack> stackList = importItems.get(i);
            compoundTag.putBoolean("IsNull", stackList == null);
            if(stackList == null) {
                list.add(compoundTag);
                continue;
            }

            ListTag stackListTag = new ListTag();
            for(int j = 0; j < stackList.size(); j++){
                CompoundTag stackTag = new CompoundTag();
                ItemStack itemStack = stackList.get(j);
                stackTag.put("Item", itemStack.serializeNBT());
                stackTag.putInt("Count", items.get(i).get(j));
                stackListTag.add(stackTag);
            }

            compoundTag.put("Stacks", stackListTag);
            list.add(compoundTag);
        }
        tag.put("ItemHandler",  list);
    }

    public void load(CompoundTag tag){
        importItems.clear();
        items.clear();

        ListTag list = tag.getList("ItemHandler", Tag.TAG_COMPOUND);
        for(int i = 0; i < 4; i++){
            CompoundTag compoundTag = list.getCompound(i);
            if(compoundTag.getBoolean("IsNull"))
                continue;

            ListTag stackListTag = compoundTag.getList("Stacks", Tag.TAG_COMPOUND);
            List<ItemStack> stackList = new ArrayList<>();
            List<Integer> items = new ArrayList<>();
            for(int j = 0; j < stackListTag.size(); j++){
                CompoundTag stackTag = stackListTag.getCompound(j);
                stackList.add(ItemStack.of(stackTag.getCompound("Item")));
                items.add(stackTag.getInt("Count"));
            }
            importItems.put(i, stackList);
            this.items.put(i, items);
        }
    }
}
