package de.dicecraft.dicemobmanager.entity.event;

import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
