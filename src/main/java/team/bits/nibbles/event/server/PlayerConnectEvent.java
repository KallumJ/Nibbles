package team.bits.nibbles.event.server;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;
import team.bits.nibbles.event.base.Event;
import team.bits.nibbles.event.base.EventListener;

import java.util.Objects;

public class PlayerConnectEvent implements Event {

    private final ServerPlayerEntity player;
    private final ClientConnection connection;

    public PlayerConnectEvent(@NotNull ServerPlayerEntity player, @NotNull ClientConnection connection) {
        this.player = Objects.requireNonNull(player);
        this.connection = Objects.requireNonNull(connection);
    }

    public @NotNull ServerPlayerEntity getPlayer() {
        return this.player;
    }

    public @NotNull ClientConnection getConnection() {
        return this.connection;
    }

    public interface Listener extends EventListener {
        void onPlayerConnect(@NotNull PlayerConnectEvent event);
    }
}
