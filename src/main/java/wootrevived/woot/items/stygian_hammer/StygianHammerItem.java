package wootrevived.woot.items.stygian_hammer;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class StygianHammerItem extends Item {

    public StygianHammerItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        return itemStack.copy();
    }
}
