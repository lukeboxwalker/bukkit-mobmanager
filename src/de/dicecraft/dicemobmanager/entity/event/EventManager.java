package de.dicecraft.dicemobmanager.entity.event;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

/**
 * Manages the custom events.
 * <p>
 * Responsible for all event listeners which
 * are registered.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public interface EventManager {

    /**
     * Broadcasts the given custom event.
     * <p>
     * Informs all registered listeners for the given
     * event and execute there event handler method
     *
     * @param event the event to broadcast
     */
    void callEvent(@Nonnull CustomEntityEvent event);

    /**
     * Registers an event listener.
     * <p>
     * Using the given plugin, to decide which plugin
     * want to register the listener.
     *
     * @param listener the event listener
     * @param plugin   the plugin which holds the listener
     */
    void registerListener(@Nonnull Listener listener, @Nonnull Plugin plugin);
}
