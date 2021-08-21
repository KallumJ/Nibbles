package team.bits.nibbles.player;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import org.jetbrains.annotations.NotNull;

/**
 * This event is called when the {@link net.minecraft.server.network.ServerPlayerEntity}
 * object gets recreated by the server (which happens for example when a player dies).
 * Any data that needs to persist on the player should be copied from the old object
 * to the new one here.
 */
public interface CopyPlayerDataEvent {

    Event<CopyPlayerDataEvent> EVENT = EventFactory.createArrayBacked(CopyPlayerDataEvent.class,
            (listeners) -> (oldPlayer, newPlayer) -> {
                for (CopyPlayerDataEvent listener : listeners) {
                    listener.copyFromOldPlayer(oldPlayer, newPlayer);
                }
            }
    );

    void copyFromOldPlayer(@NotNull INibblesPlayer oldPlayer, @NotNull INibblesPlayer newPlayer);
}
