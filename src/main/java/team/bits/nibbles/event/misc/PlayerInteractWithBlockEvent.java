package team.bits.nibbles.event.misc;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

public interface PlayerInteractWithBlockEvent {

    Event<PlayerInteractWithBlockEvent> EVENT = EventFactory.createArrayBacked(PlayerInteractWithBlockEvent.class,
            (listeners) -> (player, block, state, hand) -> {
                for (PlayerInteractWithBlockEvent event : listeners) {
                    ActionResult result = event.onPlayerInteract(player, block, state, hand);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            }
    );

    @NotNull ActionResult onPlayerInteract(@NotNull ServerPlayerEntity player, @NotNull BlockPos block, @NotNull BlockState state, @NotNull Hand hand);
}
