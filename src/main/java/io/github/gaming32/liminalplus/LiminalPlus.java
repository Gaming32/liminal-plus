package io.github.gaming32.liminalplus;

import eu.midnightdust.lib.config.MidnightConfig;
import io.github.gaming32.liminalplus.client.LiminalPlusClient;
import io.github.gaming32.liminalplus.config.LiminalPlusConfig;
import net.ludocrypt.corners.entity.DimensionalPaintingEntity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.entity_events.api.ServerEntityLoadEvents;
import org.quiltmc.qsl.lifecycle.api.event.ServerTickEvents;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.ServerPlayConnectionEvents;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

public class LiminalPlus implements ModInitializer {
    public static final String MOD_ID = "liminal-plus";

    public static final Identifier LIMINAL_REDUCED_DEBUG_INFO = new Identifier(MOD_ID, "reduced_debug_info");

    private static boolean lastTickLiminalRDI;

    @Override
    public void onInitialize(ModContainer mod) {
        MidnightConfig.init(MOD_ID, LiminalPlusConfig.class);
        lastTickLiminalRDI = LiminalPlusConfig.liminalReducedDebugInfo;

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

        ServerPlayConnectionEvents.JOIN.register(
            (handler, sender, server) -> sender.sendPacket(LIMINAL_REDUCED_DEBUG_INFO, syncLiminalRDI())
        );

        ServerTickEvents.END.register(server -> {
            if (lastTickLiminalRDI != LiminalPlusConfig.liminalReducedDebugInfo) {
                lastTickLiminalRDI = LiminalPlusConfig.liminalReducedDebugInfo;
                ServerPlayNetworking.send(server.getPlayerManager().getPlayerList(), LIMINAL_REDUCED_DEBUG_INFO, syncLiminalRDI());
            }
        });
    }

    public static boolean isLiminalReducedDebugInfo(World world) {
        final boolean enabled =
            world instanceof ServerWorld
                ? LiminalPlusConfig.liminalReducedDebugInfo
                : LiminalPlusClient.isLiminalReducedDebugInfo();
        return enabled && world.getRegistryKey().getValue().getNamespace().equals("corners");
    }

    private static PacketByteBuf syncLiminalRDI() {
        final PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(LiminalPlusConfig.liminalReducedDebugInfo);
        return buf;
    }
}
