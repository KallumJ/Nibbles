package team.bits.nibbles.event.interaction;

import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;
import team.bits.nibbles.event.base.Event;
import team.bits.nibbles.event.base.EventListener;

public class PlayerKillEntityEvent implements Event {

    private final ServerPlayerEntity player;
    private final LivingEntity killedEntity;

    public PlayerKillEntityEvent(ServerPlayerEntity player, LivingEntity killedEntity) {
        this.player = player;
        this.killedEntity = killedEntity;
    }

    public ServerPlayerEntity getPlayer() {
        return player;
    }

    public LivingEntity getKilledEntity() {
        return killedEntity;
    }

    public interface Listener extends EventListener {
        void onPlayerKillEntity(@NotNull PlayerKillEntityEvent event);
    }
}
