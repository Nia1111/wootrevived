package wootrevived.woot.util.handlers;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

public class WootItemStackHandler extends ItemStackHandler {
    protected final boolean isOutput;

    public WootItemStackHandler(boolean isOutput){
        this(1, isOutput);
    }

    public WootItemStackHandler(int size, boolean isOutput) {
        this(NonNullList.withSize(size, ItemStack.EMPTY), isOutput);
    }

    public WootItemStackHandler(NonNullList<ItemStack> stacks, boolean isOutput) {
        super(stacks);
        this.isOutput = isOutput;
    }

    public boolean isOutput(){
        return this.isOutput;
    }

    public NonNullList<ItemStack> getStacks(){
        return this.stacks;
    }
}
