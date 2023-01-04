package io.github.gaming32.liminalplus.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import eu.midnightdust.lib.config.MidnightConfig;
import io.github.gaming32.liminalplus.LiminalPlus;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class LiminalPlusModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> MidnightConfig.getScreen(parent, LiminalPlus.MOD_ID);
    }
}
