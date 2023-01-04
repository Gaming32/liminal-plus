package io.github.gaming32.liminalplus;

import eu.midnightdust.lib.config.MidnightConfig;
import io.github.gaming32.liminalplus.config.LiminalPlusConfig;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class LiminalPlus implements ModInitializer {
    public static final String MOD_ID = "liminal-plus";

    @Override
    public void onInitialize(ModContainer mod) {
        MidnightConfig.init(MOD_ID, LiminalPlusConfig.class);
    }
}
