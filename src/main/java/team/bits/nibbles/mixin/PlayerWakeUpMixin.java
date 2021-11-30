package team.bits.nibbles.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.bits.nibbles.event.base.EventManager;
import team.bits.nibbles.event.sleep.PlayerWakeUpEvent;

@Mixin(ServerPlayerEntity.class)
public class PlayerWakeUpMixin {
    @Inject(at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/PlayerEntity;wakeUp(ZZ)V"),
            method = "wakeUp"
    )
    private void onWakeUp(boolean bl, boolean updateSleepingPlayers, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        EventManager.INSTANCE.fireEvent(new PlayerWakeUpEvent(player));
    }
}
