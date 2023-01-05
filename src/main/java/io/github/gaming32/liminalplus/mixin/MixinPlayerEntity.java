package io.github.gaming32.liminalplus.mixin;

import io.github.gaming32.liminalplus.LiminalPlus;
import io.github.gaming32.liminalplus.access.PlayerEntityAccess;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends LivingEntity implements PlayerEntityAccess {
    @Shadow private boolean reducedDebugInfo;

    protected MixinPlayerEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "hasReducedDebugInfo", at = @At("HEAD"), cancellable = true)
    private void overrideRDI(CallbackInfoReturnable<Boolean> cir) {
        if (LiminalPlus.isLiminalReducedDebugInfo(getWorld())) {
            cir.setReturnValue(true);
        }
    }

    @Override
    public boolean getReducedDebugInfoField() {
        return reducedDebugInfo;
    }
}
