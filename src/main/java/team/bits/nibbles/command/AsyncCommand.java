package team.bits.nibbles.command;

import com.mojang.brigadier.context.*;
import com.mojang.brigadier.exceptions.*;
import net.minecraft.server.command.*;
import net.minecraft.text.*;
import org.apache.logging.log4j.*;
import org.jetbrains.annotations.*;

import java.util.concurrent.*;

public abstract class AsyncCommand extends Command implements AsyncCommandWrapper {

    private static final Text ERROR_TEXT = Text.literal("An error occurred while executing this command");

    private static final Logger LOGGER = LogManager.getLogger();
    private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();

    public AsyncCommand(@NotNull String name, @NotNull CommandInformation commandInfo, @NotNull String... aliases) {
        super(name, commandInfo, aliases);
    }

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        submit(this, context);
        return 1;
    }

    public static com.mojang.brigadier.Command<ServerCommandSource> wrap(@NotNull AsyncCommandWrapper wrapper) {
        return context -> {
            submit(wrapper, context);
            return 1;
        };
    }

    private static void submit(@NotNull AsyncCommandWrapper command, @NotNull CommandContext<ServerCommandSource> context) {
        EXECUTOR.submit(() -> {
            try {
                command.runAsync(context);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            } catch (CommandSyntaxException ex) {
                context.getSource().sendError(Text.literal(ex.getMessage()));
            } catch (Exception ex) {
                LOGGER.error("Error while executing async command", ex);
                context.getSource().sendError(ERROR_TEXT);
            }
        });
    }
}

