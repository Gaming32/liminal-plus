package io.github.gaming32.liminalplus.client;

import io.github.gaming32.liminalplus.LiminalPlus;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

public class LiminalPlusClient implements ClientModInitializer {
    private static boolean liminalReducedDebugInfo;

    @Override
    public void onInitializeClient(ModContainer mod) {
        ClientPlayNetworking.registerGlobalReceiver(
            LiminalPlus.LIMINAL_REDUCED_DEBUG_INFO,
            (client, handler, buf, responseSender) -> liminalReducedDebugInfo = buf.readBoolean()
        );
    }

    public static boolean isLiminalReducedDebugInfo() {
        return liminalReducedDebugInfo;
    }
}
