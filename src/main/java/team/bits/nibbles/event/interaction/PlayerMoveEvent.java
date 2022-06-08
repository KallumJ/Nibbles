package team.bits.nibbles.event.interaction;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import team.bits.nibbles.event.base.Event;
import team.bits.nibbles.event.base.EventListener;

import java.util.Objects;

public class PlayerMoveEvent implements Event {

    private final PlayerEntity player;
    private final Vec3d moveVector;

    public PlayerMoveEvent(@NotNull PlayerEntity player, @NotNull Vec3d moveVector) {
        this.player = Objects.requireNonNull(player);
        this.moveVector = Objects.requireNonNull(moveVector);
    }

    public @NotNull PlayerEntity getPlayer() {
        return this.player;
    }

    public @NotNull Vec3d getMoveVector() {
        return this.moveVector;
    }

    public interface Listener extends EventListener {
        void onPlayerMove(@NotNull PlayerMoveEvent event);
    }
}
