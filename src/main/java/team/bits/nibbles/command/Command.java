package team.bits.nibbles.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.server.command.ServerCommandSource;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static net.minecraft.server.command.CommandManager.literal;

public abstract class Command implements com.mojang.brigadier.Command<ServerCommandSource> {

    private final String name;
    private final String[] aliases;
    private final CommandInformation commandInfo;
    private final int permissionLevel;

    public Command(@NotNull String name, @NotNull CommandInformation commandInfo, @NotNull String... aliases) {
        this.name = Objects.requireNonNull(name);
        this.commandInfo = Objects.requireNonNull(commandInfo);
        this.aliases = Objects.requireNonNull(aliases);
        this.permissionLevel = commandInfo.isPublic() ? 0 : 4;
    }

    public void registerCommand(@NotNull CommandDispatcher<ServerCommandSource> dispatcher) {
        CommandNode<ServerCommandSource> commandNode = dispatcher.register(
                literal(this.name)
                        .requires(source -> source.hasPermissionLevel(this.permissionLevel))
                        .executes(this)
        );

        if (this.aliases != null) {
            registerAliases(dispatcher, commandNode);
        }
    }

    public @NotNull CommandInformation getCommandInfo() {
        return this.commandInfo;
    }

    public @NotNull String getName() {
        return this.name;
    }

    public int getPermissionLevel() {
        return this.permissionLevel;
    }

    public String[] getAliases() {
        return aliases;
    }

    protected void registerAliases(@NotNull CommandDispatcher<ServerCommandSource> dispatcher,
                                   @NotNull CommandNode<ServerCommandSource> rootNode) {
        // If aliases are provided, then use them
        if (this.aliases != null) {

            for (String alias : this.aliases) {
                Objects.requireNonNull(alias);

                // if the node has no children (arguments) we can just execute the command
                // otherwise we have to redirect the children
                if (rootNode.getChildren().isEmpty()) {
                    dispatcher.register(
                            literal(alias)
                                    .requires(source -> source.hasPermissionLevel(this.permissionLevel))
                                    .executes(rootNode.getCommand())
                    );

                } else {
                    dispatcher.register(
                            literal(alias)
                                    .requires(source -> source.hasPermissionLevel(this.permissionLevel))
                                    .redirect(rootNode)
                    );
                }
            }
        }
    }
}
