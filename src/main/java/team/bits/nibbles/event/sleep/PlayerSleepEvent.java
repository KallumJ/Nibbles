package team.bits.nibbles.event.sleep;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;

public interface PlayerSleepEvent {

    Event<PlayerSleepEvent> EVENT = EventFactory.createArrayBacked(PlayerSleepEvent.class,
            (listeners) -> (player) -> {
                for (PlayerSleepEvent listener : listeners) {
                    listener.onSleep(player);
                }
            });

    void onSleep(@NotNull PlayerEntity player);
}
