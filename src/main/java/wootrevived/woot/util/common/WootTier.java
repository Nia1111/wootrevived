package wootrevived.woot.util.common;

import wootrevived.api.enums.Tier;

import java.util.Locale;

public class WootTier {
    public static Tier[] VALUES = Tier.values();

    public static Tier byIndex(int index) {
        if (index < 0 && index >= Tier.values().length)
            return Tier.INVALID;
        return Tier.values()[index];
    }

    public static Tier getNextValid(Tier tier) {
        Tier next = VALUES[(tier.ordinal() + 1) % VALUES.length];
        if (next == Tier.INVALID)
            next = VALUES[(Tier.INVALID.ordinal() + 1) % VALUES.length];
        return next;
    }

    /**
     * check must be <= the current tier
     */
    public static String getTranslationKey(Tier tier) {
        return "misc.woot_revived." + tier.name().toLowerCase(Locale.ROOT);
    }
}
