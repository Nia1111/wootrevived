package wootrevived.woot.util.common;

import com.google.common.base.CaseFormat;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import static wootrevived.woot.util.render.WootStyles.*;

public enum MachineSideProperty {
    ENABLED,
    PUSH,
    PULL,
    DISABLED;

    public MachineSideProperty getNext(){
        if(this == ENABLED) return PUSH;
        if(this == PUSH) return PULL;
        if(this == PULL) return DISABLED;
        return ENABLED;
    }

    public static MachineSideProperty byIndex(int index) {
        if (index < 0 && index >= MachineSideProperty.values().length)
            return ENABLED;
        return MachineSideProperty.values()[index];
    }

    public MutableComponent getComponent(){
        MutableComponent component = Component.literal(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this.toString()));
        if(this == ENABLED) return component.setStyle(MACHINE_PROPERTY_ENABLED_STYLE);
        if(this == PUSH) return component.setStyle(MACHINE_PROPERTY_PUSH_STYLE);
        if(this == PULL) return component.setStyle(MACHINE_PROPERTY_PULL_STYLE);
        return component.setStyle(MACHINE_PROPERTY_DISABLED_STYLE);
    }
}
