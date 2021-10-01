package team.bits.nibbles.event.misc;

import net.minecraft.server.MinecraftServer;
import team.bits.nibbles.event.base.CustomEventFactory;
import team.bits.nibbles.event.base.NibblesEvent;
import team.bits.nibbles.utils.Scheduler;

public interface ServerTickEvent {

    NibblesEvent<ServerTickEvent> EVENT = CustomEventFactory.createCollectionBacked(ServerTickEvent.class,
            (listeners) -> (server) -> {
                for (ServerTickEvent listener : listeners) {
                    listener.onTick(server);
                }
                Scheduler.afterTick();
            }
    );

    void onTick(MinecraftServer server);
}
