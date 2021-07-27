package team.bits.nibbles.event.damage;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;

public interface PlayerDamageEvent {

    Event<PlayerDamageEvent> EVENT = EventFactory.createArrayBacked(PlayerDamageEvent.class,
            (listeners) -> (player, source, moveVector) -> {
                for (PlayerDamageEvent listener : listeners) {
                    listener.onPlayerDamage(player, source, moveVector);
                }
            }
    );

    void onPlayerDamage(@NotNull PlayerEntity player, @NotNull DamageSource source, float amount);
}
