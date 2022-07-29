package team.bits.nibbles.utils;

import net.minecraft.server.*;
import net.minecraft.server.dedicated.*;
import net.minecraft.server.network.*;
import net.minecraft.text.*;
import org.jetbrains.annotations.*;
import team.bits.nibbles.event.base.*;
import team.bits.nibbles.event.server.*;

import java.util.*;

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

    public static void broadcast(Text textComponent) {
        PlayerManager serverPlayerManager = ServerInstance.get().getPlayerManager();
        List<ServerPlayerEntity> onlinePlayers = serverPlayerManager.getPlayerList();
        onlinePlayers.forEach(player -> player.sendMessage(textComponent));
    }

    public static @NotNull Collection<ServerPlayerEntity> getOnlinePlayers() {
        return get().getPlayerManager().getPlayerList();
    }
}
