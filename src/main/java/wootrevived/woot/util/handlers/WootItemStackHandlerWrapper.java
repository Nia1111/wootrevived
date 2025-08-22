package wootrevived.woot.util.handlers;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import wootrevived.woot.util.common.MachineSideProperty;

import java.util.function.Supplier;

public class WootItemStackHandlerWrapper extends WootItemStackHandler {
    private final WootItemStackHandler handler;
    private final Supplier<MachineSideProperty> getProperty;

    public WootItemStackHandlerWrapper(WootItemStackHandler handler, Supplier<MachineSideProperty> getProperty) {
        super(handler.getStacks(), handler.isOutput());
        this.handler = handler;
        this.getProperty = getProperty;
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        MachineSideProperty property = getProperty.get();
        if(isOutput || property == MachineSideProperty.DISABLED || property == MachineSideProperty.PUSH)
            return ItemStack.EMPTY;
        return handler.insertItem(slot, stack, simulate);
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate){
        MachineSideProperty property = getProperty.get();
        if(property == MachineSideProperty.DISABLED || property == MachineSideProperty.PULL)
            return ItemStack.EMPTY;
        return handler.extractItem(slot, amount, simulate);
    }

    @Override
    public void setSize(int size) {
        handler.setSize(size);
        stacks = handler.getStacks();
    }
}
