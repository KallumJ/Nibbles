package team.bits.nibbles.utils;

import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.command.argument.MessageArgumentType.MessageFormat;
import net.minecraft.network.message.MessageType;
import net.minecraft.text.Decoration;
import net.minecraft.text.Style;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import org.jetbrains.annotations.NotNull;
import team.bits.nibbles.utils.Colors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class MessageTypes {

    public static Map<RegistryKey<MessageType>, MessageType> TMP_REGISTRY = new HashMap<>();

    public static RegistryKey<MessageType> POSITIVE = register("positive",
            createSimpleMessageType(Style.EMPTY.withColor(Colors.POSITIVE))
    );
    public static RegistryKey<MessageType> NEGATIVE = register("negative",
            createSimpleMessageType(Style.EMPTY.withColor(Colors.NEGATIVE))
    );
    public static RegistryKey<MessageType> NEUTRAL = register("neutral",
            createSimpleMessageType(Style.EMPTY.withColor(Colors.NEUTRAL))
    );
    public static RegistryKey<MessageType> HEADER = register("header",
            createSimpleMessageType(Style.EMPTY.withColor(Colors.HEADER))
    );
    public static RegistryKey<MessageType> PLAIN = register("plain",
            createSimpleMessageType(Style.EMPTY)
    );

    private MessageTypes() {
    }

    private static @NotNull RegistryKey<MessageType> register(@NotNull String name, @NotNull MessageType type) {
        Identifier identifier = new Identifier(String.format("bits_message_%s", name));
        RegistryKey<MessageType> key = RegistryKey.of(Registry.MESSAGE_TYPE_KEY, identifier);
        TMP_REGISTRY.put(key, type);
        return key;
    }

    private static @NotNull MessageType createSimpleMessageType(@NotNull Style style) {
        Decoration decoration = new Decoration(
                "%s", List.of(Decoration.Parameter.CONTENT), style
        );
        return new MessageType(
                Optional.of(MessageType.DisplayRule.of(decoration)),
                Optional.empty(),
                Optional.of(MessageType.NarrationRule.of(MessageType.NarrationRule.Kind.SYSTEM))
        );
    }
}
