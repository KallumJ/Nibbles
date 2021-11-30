package team.bits.nibbles.event.misc;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import org.jetbrains.annotations.NotNull;
import team.bits.nibbles.event.base.Event;
import team.bits.nibbles.event.base.EventListener;

import java.util.Objects;

public class CommandRegistrationEvent implements Event {

    private final CommandDispatcher<ServerCommandSource> dispatcher;
    private final boolean dedicated;

    public CommandRegistrationEvent(@NotNull CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        this.dispatcher = Objects.requireNonNull(dispatcher);
        this.dedicated = dedicated;
    }

    public @NotNull CommandDispatcher<ServerCommandSource> getDispatcher() {
        return this.dispatcher;
    }

    public boolean isDedicated() {
        return this.dedicated;
    }

    public interface Listener extends EventListener {
        void onCommandRegistration(@NotNull CommandRegistrationEvent event);
    }
}
