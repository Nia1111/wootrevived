package wootrevived.woot.items.dye_casing;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;

public class DyeCasingItem extends Item {
    final DyeColor color;

    public DyeCasingItem(DyeColor color) {
        super(new Properties().stacksTo(64));
        this.color = color;
    }

    public int getColor() {
        float[] colors = color.getTextureDiffuseColors();
        return (int)(colors[0] * 255F) << 16 | (int)(colors[1] * 255F) << 8 | (int)(colors[2] * 255F);
    }
}
