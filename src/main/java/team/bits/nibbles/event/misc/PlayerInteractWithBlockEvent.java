package team.bits.nibbles.event.misc;

import net.minecraft.block.BlockState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import team.bits.nibbles.event.base.Event;
import team.bits.nibbles.event.base.EventListener;

import java.util.Objects;

public class PlayerInteractWithBlockEvent implements Event {

    private final ServerPlayerEntity player;
    private final BlockPos block;
    private final BlockState state;
    private final Hand hand;

    public PlayerInteractWithBlockEvent(@NotNull ServerPlayerEntity player, @NotNull BlockPos block,
                                        @NotNull BlockState state, @NotNull Hand hand) {
        this.player = Objects.requireNonNull(player);
        this.block = Objects.requireNonNull(block);
        this.state = Objects.requireNonNull(state);
        this.hand = Objects.requireNonNull(hand);
    }

    public @NotNull ServerPlayerEntity getPlayer() {
        return this.player;
    }

    public @NotNull BlockPos getBlock() {
        return this.block;
    }

    public @NotNull BlockState getState() {
        return this.state;
    }

    public @NotNull Hand getHand() {
        return this.hand;
    }

    public interface Listener extends EventListener {
        void onPlayerInteractWithBlock(@NotNull PlayerInteractWithBlockEvent event);
    }
}
