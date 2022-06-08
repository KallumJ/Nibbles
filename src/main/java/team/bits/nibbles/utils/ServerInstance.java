package team.bits.nibbles.utils;

import net.minecraft.network.message.MessageType;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.registry.RegistryKey;
import org.jetbrains.annotations.NotNull;
import team.bits.nibbles.event.base.EventManager;
import team.bits.nibbles.event.server.ServerInstanceReadyEvent;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public final class ServerInstance {

    private static MinecraftDedicatedServer INSTANCE;

    private ServerInstance() {
    }

    public static void set(@NotNull MinecraftDedicatedServer server) {
        INSTANCE = Objects.requireNonNull(server);
        EventManager.INSTANCE.fireEvent(new ServerInstanceReadyEvent(server));
    }

    public static @NotNull MinecraftDedicatedServer get() {
        return Objects.requireNonNull(INSTANCE);
    }

    public static void broadcast(Text textComponent, RegistryKey<MessageType> messageType) {
        PlayerManager serverPlayerManager = ServerInstance.get().getPlayerManager();
        List<ServerPlayerEntity> onlinePlayers = serverPlayerManager.getPlayerList();
        onlinePlayers.forEach(player -> player.sendMessage(textComponent, messageType));
    }

    public static @NotNull Collection<ServerPlayerEntity> getOnlinePlayers() {
        return get().getPlayerManager().getPlayerList();
    }
}
