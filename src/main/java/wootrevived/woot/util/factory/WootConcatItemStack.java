package wootrevived.woot.util.factory;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class WootConcatItemStack {
    final Item item;
    final CompoundTag tag;

    public WootConcatItemStack(Item item, CompoundTag tag) {
        this.item = item;
        this.tag = tag;
    }

    @Override
    public int hashCode(){
        return Objects.hash(item, tag);
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof WootConcatItemStack stack)) return false;
        return item == stack.item && Objects.equals(tag, stack.tag);
    }

    public static List<ItemStack> merge(List<ItemStack> unconcatItems){
        Map<WootConcatItemStack, ItemStack> map = new HashMap<>();

        for(ItemStack itemStack : unconcatItems){
            if(itemStack.isEmpty()) continue;
            WootConcatItemStack stack = new WootConcatItemStack(itemStack.getItem(), itemStack.getTag());
            if(map.containsKey(stack)){
                ItemStack existing = map.get(stack);
                existing.grow(itemStack.getCount());
            } else {
                map.put(stack, itemStack.copy());
            }
        }

        List<ItemStack> items = new ArrayList<>();

        for(ItemStack itemStack : map.values()){
            int maxStack = itemStack.getMaxStackSize();
            int count = itemStack.getCount();

            int stacks = count / maxStack;
            int rest = count % maxStack;

            itemStack.setCount(maxStack);
            for (int i = 0; i < stacks; i++) {
                items.add(itemStack.copy());
            }

            if(rest != 0){
                itemStack.setCount(rest);
                items.add(itemStack);
            }
        }

        return items;
    }
}
