package team.bits.nibbles.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import team.bits.nibbles.event.misc.CommandRegistrationEvent;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;

public final class CommandManager implements CommandRegistrationEvent.Listener {

    public static final CommandManager INSTANCE = new CommandManager();

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Collection<Command> COMMANDS = new LinkedHashSet<>();

    private CommandManager() {
    }

    public void register(@NotNull Command command) {
        COMMANDS.add(Objects.requireNonNull(command));
    }

    @Override
    public void onCommandRegistration(@NotNull CommandRegistrationEvent event) {
        for (Command command : COMMANDS) {
            command.registerCommand(event.getDispatcher());
            LOGGER.info(String.format("Registered command '/%s'", command.getName()));
        }
    }
}
