package team.bits.nibbles.event.misc;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;

public interface ServerTickEvent {


    Event<ServerTickEvent> EVENT = EventFactory.createArrayBacked(ServerTickEvent.class,
            (listeners) -> (server) -> {
                for (ServerTickEvent listener : listeners) {
                    listener.onTick(server);
                }
            }
    );

    void onTick(MinecraftServer server);
}
