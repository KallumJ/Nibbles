package team.bits.nibbles.command;

import com.mojang.brigadier.context.*;
import com.mojang.brigadier.exceptions.*;
import net.minecraft.server.command.*;
import org.jetbrains.annotations.*;

import java.util.concurrent.*;

public interface AsyncCommandWrapper {
    void runAsync(@NotNull CommandContext<ServerCommandSource> context)
            throws CommandSyntaxException, InterruptedException, ExecutionException;
}
