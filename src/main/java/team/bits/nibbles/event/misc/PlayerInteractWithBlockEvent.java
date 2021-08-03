package team.bits.nibbles.event.misc;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.BlockState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

public interface PlayerInteractWithBlockEvent {

    Event<PlayerInteractWithBlockEvent> EVENT = EventFactory.createArrayBacked(PlayerInteractWithBlockEvent.class,
            (listeners) -> (player, connection, state) -> {
                for (PlayerInteractWithBlockEvent listener : listeners) {
                    listener.onPlayerInteract(player, connection, state);
                }
            }
    );

    void onPlayerInteract(@NotNull ServerPlayerEntity player, @NotNull BlockPos block, @NotNull BlockState state);
}
