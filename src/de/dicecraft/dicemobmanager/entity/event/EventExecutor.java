package de.dicecraft.dicemobmanager.entity.event;

import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Executor for an event handler method.
 * <p>
 * Responsible for executing the handler method
 * when the event was called.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public class EventExecutor {

    private final Plugin plugin;
    private final Listener listener;
    private final Method method;
    private final EventPriority priority;

    public EventExecutor(final Plugin plugin, final Listener listener, final Method method, final EventPriority priority) {
        this.plugin = plugin;
        this.listener = listener;
        this.method = method;
        this.priority = priority;
    }

    /**
     * Executes the handler method
     *
     * @param event the custom event that was called.
     */
    public void execute(CustomEntityEvent event) {
        try {
            method.invoke(listener, event);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public EventPriority getPriority() {
        return priority;
    }

    public Listener getListener() {
        return listener;
    }
}
