package wootrevived.woot.util.render;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

public class WootSlot extends Slot {
    private boolean isActive = true;

    public WootSlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    public void setActive(boolean isActive){
        this.isActive = isActive;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public boolean isHighlightable() {
        return isActive;
    }
}
