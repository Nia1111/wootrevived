package wootrevived.api.enums;

import java.util.EnumSet;

public enum Tier {
    INVALID,
    TIER_1,
    TIER_2,
    TIER_3,
    TIER_4,
    TIER_5;

    public int defaultVitalityCost(){
        if(this == TIER_1) return 1000;
        if(this == TIER_2) return 5000;
        if(this == TIER_3) return 10000;
        if(this == TIER_4) return 50000;
        if(this == TIER_5) return 100000;
        return 0;
    }

    private static final EnumSet<Tier> MOB_VALID_FOR_TIER_1 = EnumSet.of(TIER_1);
    private static final EnumSet<Tier> MOB_VALID_FOR_TIER_2 = EnumSet.range(TIER_1, TIER_2);
    private static final EnumSet<Tier> MOB_VALID_FOR_TIER_3 = EnumSet.range(TIER_1, TIER_3);
    private static final EnumSet<Tier> MOB_VALID_FOR_TIER_4 = EnumSet.range(TIER_1, TIER_4);
    private static final EnumSet<Tier> MOB_VALID_FOR_TIER_5 = EnumSet.range(TIER_1, TIER_5);

    public boolean isMobTierValid(Tier tier) {
        if (this == TIER_1) return MOB_VALID_FOR_TIER_1.contains(tier);
        if (this == TIER_2) return MOB_VALID_FOR_TIER_2.contains(tier);
        if (this == TIER_3) return MOB_VALID_FOR_TIER_3.contains(tier);
        if (this == TIER_4) return MOB_VALID_FOR_TIER_4.contains(tier);
        if (this == TIER_5) return MOB_VALID_FOR_TIER_5.contains(tier);
        return false;
    }

    private static final EnumSet<Tier> FACTORY_VALID_FOR_TIER_1 = EnumSet.range(Tier.TIER_1, Tier.TIER_5);
    private static final EnumSet<Tier> FACTORY_VALID_FOR_TIER_2 = EnumSet.range(Tier.TIER_2, Tier.TIER_5);
    private static final EnumSet<Tier> FACTORY_VALID_FOR_TIER_3 = EnumSet.range(Tier.TIER_3, Tier.TIER_5);
    private static final EnumSet<Tier> FACTORY_VALID_FOR_TIER_4 = EnumSet.range(Tier.TIER_4, Tier.TIER_5);
    private static final EnumSet<Tier> FACTORY_VALID_FOR_TIER_5 = EnumSet.of(Tier.TIER_5);

    public boolean isFactoryTierValid(Tier tier) {
        if (this == TIER_1) return FACTORY_VALID_FOR_TIER_1.contains(tier);
        if (this == TIER_2) return FACTORY_VALID_FOR_TIER_2.contains(tier);
        if (this == TIER_3) return FACTORY_VALID_FOR_TIER_3.contains(tier);
        if (this == TIER_4) return FACTORY_VALID_FOR_TIER_4.contains(tier);
        if (this == TIER_5) return FACTORY_VALID_FOR_TIER_5.contains(tier);
        return false;
    }
}
