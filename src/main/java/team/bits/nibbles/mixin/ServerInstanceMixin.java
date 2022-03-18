package team.bits.nibbles.mixin;

import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.bits.nibbles.utils.ServerInstance;

@Mixin(MinecraftDedicatedServer.class)
public class ServerInstanceMixin {

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    public void onServerConstruct(CallbackInfo ci) {

        ServerInstance.set((MinecraftDedicatedServer) (Object) this);
    }
}
