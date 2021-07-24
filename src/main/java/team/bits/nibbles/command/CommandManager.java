package team.bits.nibbles.command;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;

public final class CommandManager implements CommandRegistrationCallback {

    public static final CommandManager INSTANCE = new CommandManager();

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Collection<Command> COMMANDS = new LinkedHashSet<>();

    private CommandManager() {
    }

    public void register(@NotNull Command command) {
        COMMANDS.add(Objects.requireNonNull(command));
    }

    @Override
    public void register(@NotNull CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        for (Command command : COMMANDS) {
            command.registerCommand(dispatcher);
            LOGGER.info(String.format("Registered command '/%s'", command.getName()));
        }
    }
}
