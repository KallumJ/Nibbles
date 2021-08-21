package team.bits.nibbles.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.bits.nibbles.player.CopyPlayerDataEvent;
import team.bits.nibbles.player.INibblesPlayer;

import java.util.Objects;

@Mixin(ServerPlayerEntity.class)
public abstract class CopyPlayerDataMixin {

    @Inject(
            method = "copyFrom",
            at = @At("RETURN")
    )
    public void onPlayerCopy(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
        Objects.requireNonNull(oldPlayer);
        INibblesPlayer eOldPlayer = (INibblesPlayer) oldPlayer;
        INibblesPlayer eNewPlayer = (INibblesPlayer) this;
        CopyPlayerDataEvent.EVENT.invoker().copyFromOldPlayer(eOldPlayer, eNewPlayer);
    }
}
