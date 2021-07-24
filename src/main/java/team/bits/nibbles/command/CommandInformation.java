package team.bits.nibbles.command;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class CommandInformation {

    private String description;
    private String usage;
    private boolean isPublic;

    public CommandInformation setDescription(@NotNull String description) {
        this.description = Objects.requireNonNull(description);
        return this;
    }

    public CommandInformation setUsage(@NotNull String usage) {
        this.usage = Objects.requireNonNull(usage);
        return this;
    }

    public CommandInformation setPublic(boolean isPublic) {
        this.isPublic = isPublic;
        return this;
    }

    public @Nullable String getDescription() {
        return this.description;
    }

    public @Nullable String getUsage() {
        return this.usage;
    }

    public boolean isPublic() {
        return this.isPublic;
    }
}
