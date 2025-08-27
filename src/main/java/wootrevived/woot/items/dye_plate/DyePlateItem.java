package wootrevived.woot.items.dye_plate;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.MapColor;

public class DyePlateItem extends Item {
    final DyeColor color;

    public DyePlateItem(DyeColor color) {
        super(new Item.Properties().stacksTo(64));
        this.color = color;
    }

    public int getColor() {
        int rgbColor = color.getMapColor().calculateRGBColor(MapColor.Brightness.HIGH);
        return 0xFF000000 |
                ((rgbColor & 0xFF) << 16) |
                (((rgbColor >> 8) & 0xFF) << 8) |
                ((rgbColor >> 16) & 0xFF);
    }
}
