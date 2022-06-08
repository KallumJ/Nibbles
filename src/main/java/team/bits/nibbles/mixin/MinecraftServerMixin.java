package team.bits.nibbles.mixin;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.bits.nibbles.event.base.EventManager;
import team.bits.nibbles.event.server.ServerStartingEvent;
import team.bits.nibbles.event.server.ServerStoppedEvent;
import team.bits.nibbles.event.server.ServerStoppingEvent;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    @Inject(
            method = "runServer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/MinecraftServer;setupServer()Z"
            )
    )
    private void beforeSetupServer(CallbackInfo info) {
        EventManager.INSTANCE.fireEvent(new ServerStartingEvent((MinecraftServer) (Object) this));
    }

    @Inject(
            method = "shutdown",
            at = @At("HEAD")
    )
    private void beforeShutdownServer(CallbackInfo info) {
        EventManager.INSTANCE.fireEvent(new ServerStoppingEvent((MinecraftServer) (Object) this));
    }

    @Inject(
            method = "shutdown",
            at = @At("TAIL")
    )
    private void afterShutdownServer(CallbackInfo info) {
        EventManager.INSTANCE.fireEvent(new ServerStoppedEvent((MinecraftServer) (Object) this));
    }
}
