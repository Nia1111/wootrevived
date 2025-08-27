package wootrevived.woot.init;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class CommonConfig {
    private static final ModConfigSpec.Builder COMMON_BUILDER = new ModConfigSpec.Builder();

    public static ModConfigSpec COMMON_CONFIG;
    public static ModConfigSpec.BooleanValue GIVE_GUIDE_ON_SPAWN;

    static {
        COMMON_BUILDER.comment("Guide").push("guide");
        {
            GIVE_GUIDE_ON_SPAWN = COMMON_BUILDER.comment("Should give the woot guide book on player first connection")
                    .define("shouldGiveOnSpawn", true);
        }

        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    public static void init(ModContainer container){
        container.registerConfig(ModConfig.Type.COMMON, COMMON_CONFIG);
    }
}
