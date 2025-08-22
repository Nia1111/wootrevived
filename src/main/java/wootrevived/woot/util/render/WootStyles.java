package wootrevived.woot.util.render;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;

public class WootStyles {
    public static final Style CAPTURED_STYLE = Style.EMPTY.withColor(TextColor.fromLegacyFormat(ChatFormatting.GREEN));
    public static final Style SHARD_PROGRAM_STYLE = Style.EMPTY.withColor(TextColor.fromLegacyFormat(ChatFormatting.GRAY)).withItalic(true);
    public static final Style DESCRIPTION_STYLE = Style.EMPTY.withColor(TextColor.fromLegacyFormat(ChatFormatting.DARK_GRAY)).withItalic(true);
    public static final Style MACHINE_STYLE = Style.EMPTY.withColor(TextColor.fromLegacyFormat(ChatFormatting.GOLD));
    public static final Style UNIT_STYLE = Style.EMPTY.withColor(TextColor.fromLegacyFormat(ChatFormatting.DARK_AQUA));
    public static final Style DIRECTION_STYLE = Style.EMPTY.withColor(TextColor.fromLegacyFormat(ChatFormatting.GRAY));

    public static final Style MACHINE_PROPERTY_ENABLED_STYLE = Style.EMPTY.withColor(TextColor.fromLegacyFormat(ChatFormatting.GREEN));
    public static final Style MACHINE_PROPERTY_PUSH_STYLE = Style.EMPTY.withColor(TextColor.fromLegacyFormat(ChatFormatting.YELLOW));
    public static final Style MACHINE_PROPERTY_PULL_STYLE = Style.EMPTY.withColor(TextColor.fromLegacyFormat(ChatFormatting.BLUE));
    public static final Style MACHINE_PROPERTY_DISABLED_STYLE = Style.EMPTY.withColor(TextColor.fromLegacyFormat(ChatFormatting.RED));

    public static final Style MOD_NAME_STYLE = Style.EMPTY.withColor(TextColor.fromLegacyFormat(ChatFormatting.BLUE)).withItalic(true);
}
