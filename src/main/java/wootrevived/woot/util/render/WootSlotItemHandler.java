package wootrevived.woot.util.render;

import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class WootSlotItemHandler extends SlotItemHandler {
    private final Type type;
    private boolean isActive = true;

    public WootSlotItemHandler(IItemHandler itemHandler, Type type, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
        this.type = type;
    }

    public void setActive(boolean isActive){
        this.isActive = isActive;
    }

    public Type getType(){
        return type;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public boolean isHighlightable() {
        return isActive;
    }

    public enum Type {
        INVENTORY,
        HEART_INPUTS
    }
}
