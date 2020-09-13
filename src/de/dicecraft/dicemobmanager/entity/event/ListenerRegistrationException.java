package de.dicecraft.dicemobmanager.entity.event;

/**
 * Exception when the registration of an
 * event listener fails.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public class ListenerRegistrationException extends RuntimeException {

    static final long serialVersionUID = -3387516993124229948L;

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public ListenerRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message
     */
    public ListenerRegistrationException(String message) {
        super(message);
    }
}