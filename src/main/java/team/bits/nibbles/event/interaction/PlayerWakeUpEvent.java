package team.bits.nibbles.event.interaction;

import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;
import team.bits.nibbles.event.base.Event;
import team.bits.nibbles.event.base.EventListener;

import java.util.Objects;

public class PlayerWakeUpEvent implements Event {

    private final PlayerEntity player;

    public PlayerWakeUpEvent(@NotNull PlayerEntity player) {
        this.player = Objects.requireNonNull(player);
    }

    public @NotNull PlayerEntity getPlayer() {
        return this.player;
    }

    public interface Listener extends EventListener {
        void onPlayerWakeUp(@NotNull PlayerWakeUpEvent event);
    }
}
