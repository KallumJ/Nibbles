package team.bits.nibbles.event.damage;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;
import team.bits.nibbles.event.base.Event;

import java.util.Objects;

public class PlayerDamageEvent implements Event {

    private final PlayerEntity player;
    private final DamageSource source;
    private final float amount;

    public PlayerDamageEvent(@NotNull PlayerEntity player, @NotNull DamageSource source, float amount) {
        this.player = Objects.requireNonNull(player);
        this.source = Objects.requireNonNull(source);
        this.amount = amount;
    }

    public @NotNull PlayerEntity getPlayer() {
        return this.player;
    }

    public @NotNull DamageSource getSource() {
        return this.source;
    }

    public float getAmount() {
        return this.amount;
    }

    public interface Listener {
        void onPlayerDamage(@NotNull PlayerDamageEvent event);
    }
}
