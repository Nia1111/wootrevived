package wootrevived.woot.items.basic;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BasicItem extends Item {
    final Type itemType;

    public BasicItem(Type itemType, int stackSize) {
        super(new Properties().stacksTo(stackSize));
        this.itemType = itemType;
    }

    public BasicItem(Type itemType) { this(itemType, 64); }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return itemType == Type.COPPER_ENCHANTED_PLATE ||
               itemType == Type.IRON_ENCHANTED_PLATE ||
               itemType == Type.GOLD_ENCHANTED_PLATE ||
               itemType == Type.DIAMOND_ENCHANTED_PLATE ||
               itemType == Type.NETHERITE_ENCHANTED_PLATE;
    }

    public enum Type {
        STYGIAN_INGOT,
        STYGIAN_DUST,
        STYGIAN_PLATE,
        PRISM,
        COPPER_ENCHANTED_PLATE,
        IRON_ENCHANTED_PLATE,
        GOLD_ENCHANTED_PLATE,
        DIAMOND_ENCHANTED_PLATE,
        NETHERITE_ENCHANTED_PLATE,
        COPPER_SHARD,
        IRON_SHARD,
        GOLD_SHARD,
        DIAMOND_SHARD,
        NETHERITE_SHARD,
        UPGRADE_BASE
    }
}
