package team.bits.nibbles.event.sleep;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;

public interface PlayerWakeUpEvent {

    Event<PlayerWakeUpEvent> EVENT = EventFactory.createArrayBacked(PlayerWakeUpEvent.class,
            (listeners) -> (player) -> {
                for (PlayerWakeUpEvent listener : listeners) {
                    listener.onWakeUp(player);
                }
            });

    void onWakeUp(@NotNull PlayerEntity player);
}
