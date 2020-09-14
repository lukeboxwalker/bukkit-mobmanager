package de.dicecraft.dicemobmanager.entity.event;

import org.bukkit.event.EventPriority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a event handler method.
 * <p>
 * Can specify a priority to affect the
 * order of execution.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomEventHandler {

    /**
     * Specifies the event priority.
     * <p>
     * Default priority is set to normal.
     * Priorities form low to high:
     * -LOWEST
     * -LOW
     * -NORMAL
     * -HIGH
     * -HIGHEST
     * -MONITOR
     *
     * @return the event priority
     */
    EventPriority priority() default EventPriority.NORMAL;
}
