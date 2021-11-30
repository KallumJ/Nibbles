package team.bits.nibbles.mixin;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.bits.nibbles.event.base.EventManager;
import team.bits.nibbles.event.misc.CommandRegistrationEvent;

@Mixin(CommandManager.class)
public class CommandRegistrationMixin {

    @Shadow
    @Final
    private CommandDispatcher<ServerCommandSource> dispatcher;

    @Inject(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/brigadier/CommandDispatcher;findAmbiguities(Lcom/mojang/brigadier/AmbiguityConsumer;)V"
            )
    )
    private void onCommandRegistration(CommandManager.RegistrationEnvironment environment, CallbackInfo ci) {
        EventManager.INSTANCE.fireEvent(new CommandRegistrationEvent(this.dispatcher, environment == CommandManager.RegistrationEnvironment.DEDICATED));
    }
}
