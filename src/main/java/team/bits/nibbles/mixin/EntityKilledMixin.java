package team.bits.nibbles.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.core.net.Severity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.bits.nibbles.event.base.EventManager;
import team.bits.nibbles.event.interaction.PlayerKillEntityEvent;
import team.bits.nibbles.event.server.PlayerDisconnectEvent;

@Mixin(LivingEntity.class)
public class EntityKilledMixin {

    @Inject(
            method = "onDeath",
            at = @At("TAIL")
    )
    public void onDeath(DamageSource damageSource, CallbackInfo ci) {
        LivingEntity killedEntity = (LivingEntity) (Object) this;

        if (damageSource.getSource() instanceof ServerPlayerEntity player) {
            EventManager.INSTANCE.fireEvent(new PlayerKillEntityEvent(player, killedEntity));
        }
    }
}
