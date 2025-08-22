package wootrevived.woot.util.recipes;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

public class WootContainer implements Container {
    private final int size;
    private final NonNullList<Either<ItemStack, FluidStack>> ingredients;

    public WootContainer(int size) {
        this.size = size;
        this.ingredients = NonNullList.withSize(size, Either.left(ItemStack.EMPTY));
    }

    @SafeVarargs
    public WootContainer(Either<ItemStack, FluidStack>... ingredients) {
        this.size = ingredients.length;
        this.ingredients = NonNullList.of(Either.left(ItemStack.EMPTY), ingredients);
    }

    @Override
    public int getContainerSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public boolean isItemStack(int slot){
        return slot >= 0 && slot < ingredients.size() && ingredients.get(slot).left().isPresent();
    }

    public boolean isFluidStack(int slot){
        return slot >= 0 && slot < ingredients.size() && ingredients.get(slot).right().isPresent();
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return slot >= 0 && slot < ingredients.size() ? this.ingredients.get(slot).left().orElse(ItemStack.EMPTY) : ItemStack.EMPTY;
    }

    public FluidStack getFluid(int slot) {
        return slot >= 0 && slot < ingredients.size() ? this.ingredients.get(slot).right().orElse(FluidStack.EMPTY) : FluidStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack removeItem(int slot, int amount) {
        ItemStack itemStack = isItemStack(slot) && amount > 0 ? ingredients.get(slot).left().get().split(amount) : ItemStack.EMPTY;
        if(!itemStack.isEmpty())
            setChanged();
        return itemStack;
    }

    public FluidStack removeFluid(int slot, int amount) {
        FluidStack fluidStack = isFluidStack(slot) && amount > 0 ? ingredients.get(slot).right().get() : FluidStack.EMPTY;
        if(!fluidStack.isEmpty()) {
            fluidStack.setAmount(fluidStack.getAmount() - amount);
            setChanged();
        }
        return fluidStack;
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int slot) {
        if(isItemStack(slot)){
            ItemStack itemstack = ingredients.get(slot).left().get();
            if(!itemstack.isEmpty())
                ingredients.set(0, Either.left(ItemStack.EMPTY));
            return itemstack;
        }
        return ItemStack.EMPTY;
    }

    public FluidStack removeFluidNoUpdate(int slot) {
        if(isFluidStack(slot)){
            FluidStack fluidStack = ingredients.get(slot).right().get();
            if(!fluidStack.isEmpty())
                ingredients.set(0, Either.right(FluidStack.EMPTY));
            return fluidStack;
        }
        return FluidStack.EMPTY;
    }

    @Override
    public void setItem(int slot, @NotNull ItemStack stack) {
        ingredients.set(slot, Either.left(stack));
        if(!stack.isEmpty() && stack.getCount() > getMaxStackSize()){
            stack.setCount(getMaxStackSize());
        }
        setChanged();
    }

    public void setFluid(int slot, FluidStack stack) {
        ingredients.set(slot, Either.right(stack));
        setChanged();
    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        ingredients.clear();
        setChanged();
    }
}
