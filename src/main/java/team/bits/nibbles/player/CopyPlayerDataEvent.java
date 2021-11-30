package team.bits.nibbles.player;


import org.jetbrains.annotations.NotNull;
import team.bits.nibbles.event.base.Event;
import team.bits.nibbles.event.base.EventListener;

import java.util.Objects;

/**
 * This event is called when the {@link net.minecraft.server.network.ServerPlayerEntity}
 * object gets recreated by the server (which happens for example when a player dies).
 * Any data that needs to persist on the player should be copied from the old object
 * to the new one here.
 */
public class CopyPlayerDataEvent implements Event {

    private final INibblesPlayer oldPlayer;
    private final INibblesPlayer newPlayer;

    public CopyPlayerDataEvent(@NotNull INibblesPlayer oldPlayer, @NotNull INibblesPlayer newPlayer) {
        this.oldPlayer = Objects.requireNonNull(oldPlayer);
        this.newPlayer = Objects.requireNonNull(newPlayer);
    }

    public @NotNull INibblesPlayer getOldPlayer() {
        return this.oldPlayer;
    }

    public @NotNull INibblesPlayer getNewPlayer() {
        return this.newPlayer;
    }

    public interface Listener extends EventListener {
        void onCopyPlayerData(@NotNull CopyPlayerDataEvent event);
    }
}
