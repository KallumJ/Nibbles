package team.bits.nibbles.mixin;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.bits.nibbles.event.base.EventManager;
import team.bits.nibbles.event.server.PlayerDisconnectEvent;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class PlayerDisconnectMixin {

    @Shadow
    public abstract ServerPlayerEntity getPlayer();

    @Shadow
    public abstract ClientConnection getConnection();

    @Inject(
            method = "onDisconnected",
            at = @At("HEAD")
    )
    public void onDisconnect(Text reason, CallbackInfo ci) {
        EventManager.INSTANCE.fireEvent(new PlayerDisconnectEvent(this.getPlayer(), this.getConnection()));
    }
}
