package team.bits.nibbles.event.damage;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;
import team.bits.nibbles.event.base.CustomEventFactory;
import team.bits.nibbles.event.base.NibblesEvent;

public interface PlayerDamageEvent {

    NibblesEvent<PlayerDamageEvent> EVENT = CustomEventFactory.createCollectionBacked(PlayerDamageEvent.class,
            (listeners) -> (player, source, moveVector) -> {
                for (PlayerDamageEvent listener : listeners) {
                    listener.onPlayerDamage(player, source, moveVector);
                }
            }
    );

    void onPlayerDamage(@NotNull PlayerEntity player, @NotNull DamageSource source, float amount);
}
