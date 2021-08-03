package team.bits.nibbles.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import team.bits.nibbles.event.misc.PlayerInteractWithBlockEvent;

@Mixin(ServerPlayerInteractionManager.class)
public class PlayerInteractWithBlockMixin {

    @Inject(
            method = "interactBlock",
            at = @At("HEAD")
    )
    public void onInteractBlock(ServerPlayerEntity player, World world, ItemStack stack, Hand hand,
                                BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {

        final BlockPos pos = hitResult.getBlockPos();
        final BlockState blockState = world.getBlockState(pos);

        PlayerInteractWithBlockEvent.EVENT.invoker().onPlayerInteract(player, pos, blockState);
    }
}
