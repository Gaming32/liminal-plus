package io.github.gaming32.liminalplus.mixin;

import io.github.gaming32.liminalplus.LiminalPlus;
import io.github.gaming32.liminalplus.config.LiminalPlusConfig;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class MixinServerPlayerEntity {
    @Unique
    private boolean lastLiminalReducedDebugInfo = false;

    @Inject(method = "tick", at = @At("TAIL"))
    private void liminalTick(CallbackInfo ci) {
        final boolean shouldHaveReducedDebugInfo = LiminalPlusConfig.liminalReducedDebugInfo;
        if (shouldHaveReducedDebugInfo != lastLiminalReducedDebugInfo) {
            lastLiminalReducedDebugInfo = shouldHaveReducedDebugInfo;

            final PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBoolean(shouldHaveReducedDebugInfo);
            ServerPlayNetworking.send((ServerPlayerEntity)(Object)this, LiminalPlus.LIMINAL_REDUCED_DEBUG_INFO, buf);
        }
    }
}
