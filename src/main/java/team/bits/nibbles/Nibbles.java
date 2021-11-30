package team.bits.nibbles;

import net.fabricmc.api.ModInitializer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.fabric.FabricServerAudiences;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.command.ServerCommandSource;
import org.jetbrains.annotations.NotNull;
import team.bits.nibbles.command.CommandManager;
import team.bits.nibbles.event.base.EventManager;
import team.bits.nibbles.event.misc.ServerStartingEvent;
import team.bits.nibbles.event.misc.ServerStoppedEvent;
import team.bits.nibbles.event.misc.ServerTickEvent;
import team.bits.nibbles.utils.Scheduler;

public class Nibbles implements ModInitializer {

    private static FabricServerAudiences adventure;

    public static FabricServerAudiences adventure() {
        if (adventure == null) {
            throw new IllegalStateException("Tried to access Adventure without a running server!");
        }
        return adventure;
    }

    public static @NotNull Audience audience(@NotNull CommandOutput source) {
        return adventure().audience(source);
    }

    public static @NotNull Audience audience(@NotNull ServerCommandSource source) {
        return adventure().audience(source);
    }

    @Override
    public void onInitialize() {
        EventManager.INSTANCE.registerEvents((ServerStartingEvent.Listener) event -> adventure = FabricServerAudiences.of(event.getServer()));
        EventManager.INSTANCE.registerEvents((ServerStoppedEvent.Listener) event -> adventure = null);

        EventManager.INSTANCE.registerEvents((ServerTickEvent.Listener) event -> Scheduler.tick());

        EventManager.INSTANCE.registerEvents(CommandManager.INSTANCE);
    }
}
