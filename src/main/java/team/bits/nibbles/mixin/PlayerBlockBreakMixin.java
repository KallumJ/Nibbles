package team.bits.nibbles.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import team.bits.nibbles.event.base.EventManager;
import team.bits.nibbles.event.interaction.PlayerBlockBreakEvent;

@Mixin(ServerPlayerInteractionManager.class)
public class PlayerBlockBreakMixin {

    @Shadow
    protected ServerWorld world;

    @Final
    @Shadow
    protected ServerPlayerEntity player;

    @Inject(
            method = "tryBreakBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/Block;onBroken(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    public void onBreakBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir, BlockState blockState, BlockEntity blockEntity, Block block, boolean bl) {
        EventManager.INSTANCE.fireEvent(new PlayerBlockBreakEvent(this.player, world, pos, blockState, blockEntity));
    }
}
