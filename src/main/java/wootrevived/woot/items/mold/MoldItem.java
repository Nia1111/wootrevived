package wootrevived.woot.items.mold;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * These items are NEVER consumed in recipes
 */
public class MoldItem extends Item {

    final MoldType moldType;
    public MoldItem(MoldType moldType) {
        super(new Item.Properties().stacksTo(1));
        this.moldType = moldType;
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        return itemStack.copy();
    }

    public enum MoldType {
        PLATE,
        SHARD,
        DYE_CASING
    }
}
