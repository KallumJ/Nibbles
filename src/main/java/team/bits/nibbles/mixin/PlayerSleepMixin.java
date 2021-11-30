package team.bits.nibbles.mixin;

import com.mojang.datafixers.util.Either;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import team.bits.nibbles.event.base.EventManager;
import team.bits.nibbles.event.sleep.PlayerSleepEvent;

@Mixin(ServerPlayerEntity.class)
public class PlayerSleepMixin {

    @Inject(at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/world/ServerWorld;updateSleepingPlayers()V"),
            method = "trySleep"
    )
    private void onSleep(BlockPos pos,
                         CallbackInfoReturnable<Either<PlayerEntity.SleepFailureReason, Unit>> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        EventManager.INSTANCE.fireEvent(new PlayerSleepEvent(player));
    }
}
