package team.bits.nibbles.mixin;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.bits.nibbles.event.base.EventManager;
import team.bits.nibbles.event.misc.ServerTickEvent;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public class ServerTickMixin {

    @Inject(
            at = @At("RETURN"),
            method = "tick"
    )
    public void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        MinecraftServer server = (MinecraftServer) (Object) this;
        EventManager.INSTANCE.fireEvent(new ServerTickEvent(server));
    }
}
