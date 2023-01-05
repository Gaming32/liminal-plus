package io.github.gaming32.liminalplus;

import eu.midnightdust.lib.config.MidnightConfig;
import io.github.gaming32.liminalplus.config.LiminalPlusConfig;
import net.ludocrypt.corners.entity.DimensionalPaintingEntity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.util.registry.Registry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.entity_events.api.ServerEntityLoadEvents;

public class LiminalPlus implements ModInitializer {
    public static final String MOD_ID = "liminal-plus";

    @Override
    public void onInitialize(ModContainer mod) {
        MidnightConfig.init(MOD_ID, LiminalPlusConfig.class);

        ServerEntityLoadEvents.AFTER_LOAD.register((entity, world) -> {
            if (
                LiminalPlusConfig.automaticPaintingPortal &&
                    entity instanceof PaintingEntity painting &&
                    !(entity instanceof DimensionalPaintingEntity) &&
                    Registry.PAINTING_VARIANT.getId(painting.getVariant().value()).getNamespace().equals("corners")
            ) {
                final DimensionalPaintingEntity newEntity = DimensionalPaintingEntity.create(
                    world, painting.getDecorationBlockPos(), painting.getHorizontalFacing(), painting.getVariant().value()
                );
                world.spawnEntity(newEntity);
                entity.discard();
            }
        });
    }
}
