package team.bits.nibbles.event.base;

public class InternalEventException extends RuntimeException {

    public InternalEventException() {
    }

    public InternalEventException(String message) {
        super(message);
    }

    public InternalEventException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalEventException(Throwable cause) {
        super(cause);
    }

    public InternalEventException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
