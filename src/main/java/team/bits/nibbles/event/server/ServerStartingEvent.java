package team.bits.nibbles.event.server;

import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;
import team.bits.nibbles.event.base.Event;
import team.bits.nibbles.event.base.EventListener;

import java.util.Objects;

public class ServerStartingEvent implements Event {

    private final MinecraftServer server;

    public ServerStartingEvent(@NotNull MinecraftServer server) {
        this.server = Objects.requireNonNull(server);
    }

    public @NotNull MinecraftServer getServer() {
        return this.server;
    }

    public interface Listener extends EventListener {
        void onServerStarting(@NotNull ServerStartingEvent event);
    }
}
