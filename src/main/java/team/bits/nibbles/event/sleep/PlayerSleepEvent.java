package team.bits.nibbles.event.sleep;

import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;
import team.bits.nibbles.event.base.Event;
import team.bits.nibbles.event.base.EventListener;

import java.util.Objects;

public class PlayerSleepEvent implements Event {

    private final PlayerEntity player;

    public PlayerSleepEvent(@NotNull PlayerEntity player) {
        this.player = Objects.requireNonNull(player);
    }

    public @NotNull PlayerEntity getPlayer() {
        return this.player;
    }

    public interface Listener extends EventListener {
        void onPlayerSleep(@NotNull PlayerSleepEvent event);
    }
}
