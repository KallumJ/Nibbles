package team.bits.nibbles.mixin;

import net.minecraft.network.message.MessageType;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import team.bits.nibbles.utils.MessageTypes;

import java.util.Map;

@Mixin(MessageType.class)
public class MessageTypeMixin {

    @Inject(
            method = "initialize",
            at = @At("RETURN")
    )
    private static void onRegisterMessageTypes(Registry<MessageType> registry, CallbackInfoReturnable<MessageType> cir) {
        for (Map.Entry<RegistryKey<MessageType>, MessageType> entry : MessageTypes.TMP_REGISTRY.entrySet()) {
            Registry.register(registry, entry.getKey(), entry.getValue());
        }
    }
}
