package team.bits.nibbles.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.bits.nibbles.event.base.EventManager;
import team.bits.nibbles.event.interaction.PlayerInteractWithBlockEvent;

@Mixin(ServerPlayNetworkHandler.class)
public class PlayerInteractWithBlockMixin {

    @Shadow
    public ServerPlayerEntity player;

    @Inject(
            method = "onPlayerInteractBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/packet/c2s/play/PlayerInteractBlockC2SPacket;getBlockHitResult()Lnet/minecraft/util/hit/BlockHitResult;"
            )
    )
    public void onInteractBlock(PlayerInteractBlockC2SPacket packet, CallbackInfo ci) {

        final BlockHitResult hitResult = packet.getBlockHitResult();
        final Hand hand = packet.getHand();
        final BlockPos pos = hitResult.getBlockPos();
        final BlockState blockState = this.player.world.getBlockState(pos);

        EventManager.INSTANCE.fireEvent(new PlayerInteractWithBlockEvent(this.player, pos, blockState, hand));
    }
}
