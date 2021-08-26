package team.bits.nibbles.database.driver;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;

public interface IDatabaseDriver {

    /**
     * Open the connection to the database server.
     *
     * @throws DatabaseDriverException if the connection cannot be established
     */
    void open() throws DatabaseDriverException;

    /**
     * Closes the connection to the database server. Note that after
     * calling this method, calling {@link IDatabaseDriver#getConnection()}
     * will throw an exception.
     *
     * @throws DatabaseDriverException if an error occurs while closing the connection
     */
    void close() throws DatabaseDriverException;

    /**
     * Returns the active connection to the database server.
     *
     * @return an active {@link Connection} object
     * @throws DatabaseDriverException if there is no open connection to the database server
     */
    @NotNull Connection getConnection() throws DatabaseDriverException;

    /**
     * @return the properties of the database connection for this driver
     */
    @NotNull DatabaseProperties getProperties();
}
