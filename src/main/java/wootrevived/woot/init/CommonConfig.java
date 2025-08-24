package wootrevived.woot.init;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class CommonConfig {
    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec.BooleanValue GIVE_GUIDE_ON_SPAWN;

    static {
        COMMON_BUILDER.comment("Guide").push("guide");
        {
            GIVE_GUIDE_ON_SPAWN = COMMON_BUILDER.comment("Should give the woot guide book on player first connection")
                    .define("shouldGiveOnSpawn", true);
        }

        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    public static void init(){
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_CONFIG);
    }
}
