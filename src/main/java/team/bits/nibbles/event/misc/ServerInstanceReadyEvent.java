package team.bits.nibbles.event.misc;

import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import org.jetbrains.annotations.NotNull;
import team.bits.nibbles.event.base.Event;
import team.bits.nibbles.event.base.EventListener;

import java.util.Objects;

public class ServerInstanceReadyEvent implements Event {

    private final MinecraftDedicatedServer server;

    public ServerInstanceReadyEvent(@NotNull MinecraftDedicatedServer server) {
        this.server = Objects.requireNonNull(server);
    }

    public @NotNull MinecraftDedicatedServer getServer() {
        return this.server;
    }

    public interface Listener extends EventListener {
        void onServerInstanceReady(@NotNull ServerInstanceReadyEvent event);
    }
}
