package team.bits.nibbles.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.bits.nibbles.event.damage.PlayerDamageEvent;
import team.bits.nibbles.event.misc.PlayerMoveEvent;

@Mixin(PlayerEntity.class)
public class PlayerDamageMixin {

    /**
     * Caller for the {@link PlayerDamageEvent}
     */
    @Inject(
            method = "applyDamage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;addExhaustion(F)V"
            )
    )
    private void onDamage(DamageSource source, float amount, CallbackInfo ci) {
        PlayerEntity player = PlayerEntity.class.cast(this);
        PlayerDamageEvent.EVENT.invoker().onPlayerDamage(player, source, amount);
    }
}