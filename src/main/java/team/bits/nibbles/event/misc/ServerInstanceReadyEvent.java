package team.bits.nibbles.event.misc;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import org.jetbrains.annotations.NotNull;

public interface ServerInstanceReadyEvent {
    Event<ServerInstanceReadyEvent> EVENT = EventFactory.createArrayBacked(ServerInstanceReadyEvent.class,
            (listeners) -> (server) -> {
                for (ServerInstanceReadyEvent listener : listeners) {
                    listener.onServerInstanceReady(server);
                }
            }
    );

    void onServerInstanceReady(@NotNull MinecraftDedicatedServer server);
}
