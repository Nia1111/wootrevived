package wootrevived.woot.util.common;

import net.minecraft.network.chat.Component;

public enum RedstoneMode {
    ALWAYS_ON,
    WITH_NO_SIGNAL,
    WITH_SIGNAL,
    ONCE;

    public RedstoneMode getNext(){
        if(this == ALWAYS_ON) return WITH_NO_SIGNAL;
        if(this == WITH_NO_SIGNAL) return WITH_SIGNAL;
        if(this == WITH_SIGNAL) return ONCE;
        return ALWAYS_ON;
    }

    public Component getComponent(){
        return Component.translatable("gui.woot_revived.redstone." + this.toString().toLowerCase());
    }

    public static RedstoneMode byIndex(int index) {
        if (index < 0 && index >= RedstoneMode.values().length)
            return ALWAYS_ON;
        return RedstoneMode.values()[index];
    }
}
