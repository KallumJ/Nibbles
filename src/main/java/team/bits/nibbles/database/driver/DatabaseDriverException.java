package team.bits.nibbles.database.driver;

public class DatabaseDriverException extends RuntimeException {

    protected DatabaseDriverException(String message) {
        super(message);
    }

    protected DatabaseDriverException(String message, Throwable cause) {
        super(message, cause);
    }
}
