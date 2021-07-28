package team.bits.nibbles.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.bits.nibbles.event.misc.PlayerMoveEvent;

@Mixin(PlayerEntity.class)
public class PlayerMoveMixin {
    private Vec3d previousPos = Vec3d.ZERO;

    /**
     * Caller for the {@link PlayerMoveEvent}
     */
    @Inject(
            method = "tickMovement",
            at = @At("TAIL")
    )
    private void onTickMovement(CallbackInfo ci) {
        PlayerEntity player = PlayerEntity.class.cast(this);
        Vec3d currentPos = player.getPos();
        Vec3d moveVector = this.previousPos.subtract(currentPos);

        // only trigger if the player moved more than 1/10'th of a block
        if (moveVector.length() > 0.1) {
            PlayerMoveEvent.EVENT.invoker().onPlayerMove(player, moveVector);
        }

        this.previousPos = currentPos;
    }
}