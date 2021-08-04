package team.bits.nibbles.database.driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

class DatabaseDriver implements IDatabaseDriver {

    static final Logger LOGGER = LogManager.getLogger();

    private final DatabaseProperties properties;

    private Connection connection;

    public DatabaseDriver(@NotNull DatabaseProperties properties) {
        this.properties = Objects.requireNonNull(properties);
    }

    @Override
    public void open() throws DatabaseDriverException {
        if (this.connection != null) {
            throw new DatabaseDriverException("Database connection has already been established");
        }

        String url = String.format("jdbc:mysql://%s:%s/%s?useSSL=false&autoReconnect=true",
                this.properties.address(),
                this.properties.port(),
                this.properties.databaseName()
        );

        try {
            this.connection = DriverManager.getConnection(url,
                    this.properties.username(),
                    this.properties.password()
            );
        } catch (Exception ex) {
            throw new DatabaseDriverException("Unable to establish database connection", ex);
        }

        LOGGER.info(String.format("Database connection %s@%s established",
                this.properties.username(), this.properties.address()
        ));
    }

    @Override
    public void close() throws DatabaseDriverException {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                this.connection.close();
                LOGGER.info("Database connection closed");
            }
        } catch (SQLException ex) {
            throw new DatabaseDriverException("SQLException while closing database connection", ex);
        }
    }

    @Override
    public @NotNull Connection getConnection() {
        if (this.connection == null) {
            throw new DatabaseDriverException("Database connection hasn't been opened yet");
        }
        return this.connection;
    }
}
