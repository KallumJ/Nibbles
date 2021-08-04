package team.bits.nibbles.database.driver;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Properties;

public record DatabaseProperties(String address, short port, String databaseName,
                                 String username, String password) {

    public DatabaseProperties(@NotNull String address, short port, @NotNull String databaseName, @NotNull String username, @NotNull String password) {
        this.address = Objects.requireNonNull(address);
        this.port = port;
        this.databaseName = Objects.requireNonNull(databaseName);
        this.username = Objects.requireNonNull(username);
        this.password = Objects.requireNonNull(password);
    }

    public DatabaseProperties(@NotNull Properties properties) {
        this(
                properties.getProperty("db.address"),
                Short.parseShort(properties.getProperty("db.port")),
                properties.getProperty("db.name"),
                properties.getProperty("db.username"),
                properties.getProperty("db.password")
        );
    }
}
