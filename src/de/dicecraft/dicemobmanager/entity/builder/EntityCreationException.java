package de.dicecraft.dicemobmanager.entity.builder;

/**
 * Exception when the entity creation fails.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public class EntityCreationException extends RuntimeException {

    static final long serialVersionUID = -3387516993124229948L;

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public EntityCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message
     */
    public EntityCreationException(String message) {
        super(message);
    }
}
