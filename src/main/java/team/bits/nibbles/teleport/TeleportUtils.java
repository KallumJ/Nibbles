package team.bits.nibbles.teleport;

import net.kyori.adventure.text.Component;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import team.bits.nibbles.Nibbles;

import java.util.List;
import java.util.Objects;

public final class TeleportUtils {

    private static final int PET_TELEPORT_CHUNK_RADIUS = 2;

    private TeleportUtils() {
    }

    /**
     * Teleport a player to a given {@link Location}. Works across dimensions.
     *
     * @param player   the {@link ServerPlayerEntity} to teleport
     * @param location the {@link Location} to teleport to
     */
    public static void teleport(@NotNull ServerPlayerEntity player, @NotNull Location location) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(location);

        final ServerWorld world = location.world();
        final Vec3d position = location.position();

        // Get the players original chunk position
        ChunkPos originalChunkPos = player.getChunkPos();

        // load the chunk at the destination
        ChunkPos chunkPos = new ChunkPos(new BlockPos(position));
        world.getChunkManager().addTicket(ChunkTicketType.POST_TELEPORT,
                chunkPos, 1, player.getId()
        );

        // make sure the player isn't riding an entity or sleeping
        player.stopRiding();
        if (player.isSleeping()) {
            player.wakeUp(true, true);
        }

        // teleport the player to the destination
        player.teleport(world, position.x, position.y, position.z, player.getYaw(), player.getPitch());

        // Load chunk at original chunk, to teleport any left behind pets
        world.getChunkManager().addTicket(ChunkTicketType.POST_TELEPORT,
                originalChunkPos, PET_TELEPORT_CHUNK_RADIUS, player.getId()
        );

        // trigger synchronization of experience and health
        player.addExperience(0);
        player.markHealthDirty();
    }
}
