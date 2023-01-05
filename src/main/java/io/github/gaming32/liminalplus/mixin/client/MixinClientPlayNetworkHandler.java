package io.github.gaming32.liminalplus.mixin.client;

import io.github.gaming32.liminalplus.access.PlayerEntityAccess;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler {
    @Redirect(
        method = "onPlayerRespawn",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/network/ClientPlayerEntity;hasReducedDebugInfo()Z"
        )
    )
    private boolean useRDIField(ClientPlayerEntity instance) {
        return ((PlayerEntityAccess)instance).getReducedDebugInfoField();
    }
}
