package team.bits.nibbles.event.interaction;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import team.bits.nibbles.event.base.Event;
import team.bits.nibbles.event.base.EventListener;

public class PlayerBlockBreakEvent implements Event {
    private final ServerPlayerEntity player;
    private final ServerWorld world;
    private final BlockPos pos;
    private final BlockState blockState;
    private final BlockEntity blockEntity;

    public PlayerBlockBreakEvent(ServerPlayerEntity player, ServerWorld world, BlockPos pos, BlockState blockState, BlockEntity blockEntity) {
        this.player = player;
        this.world = world;
        this.pos = pos;
        this.blockState = blockState;
        this.blockEntity = blockEntity;
    }

    public ServerPlayerEntity getPlayer() {
        return player;
    }

    public ServerWorld getWorld() {
        return world;
    }

    public BlockPos getPos() {
        return pos;
    }

    public BlockState getBlockState() {
        return blockState;
    }

    public BlockEntity getBlockEntity() {
        return blockEntity;
    }

    public interface Listener extends EventListener {
        void onBlockBroken(@NotNull PlayerBlockBreakEvent event);
    }
}
