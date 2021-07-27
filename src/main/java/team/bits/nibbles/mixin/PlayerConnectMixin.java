package team.bits.nibbles.mixin;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.bits.nibbles.event.misc.PlayerConnectEvent;

@Mixin(PlayerManager.class)
public class PlayerConnectMixin {

    @Inject(
            method = "onPlayerConnect",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerWorld;onPlayerConnected(Lnet/minecraft/server/network/ServerPlayerEntity;)V"
            )
    )
    public void onPlayerJoin(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        PlayerConnectEvent.EVENT.invoker().onPlayerConnect(player, connection);
    }
}
