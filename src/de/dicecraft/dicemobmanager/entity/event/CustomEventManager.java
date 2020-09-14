package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.DiceMobManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Manages the custom events.
 * <p>
 * Responsible for all event listeners which
 * are registered.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public final class CustomEventManager implements EventManager {

    // executor map containing all events in relation to there event executor
    // all executors are stored in a priority queue sorted by there event priority
    private final HashMap<Class<? extends CustomEntityEvent>, Queue<EventExecutor>> eventExecutors;
    private final Comparator<EventExecutor> comparator = Comparator.comparingInt(eventExecutor -> eventExecutor.getPriority().getSlot());
    private final Plugin plugin;

    /**
     * Creates a new custom event manager.
     * <p>
     * Inits the event executor map as an empty hashmap
     *
     * @param plugin the mob manager plugin
     */
    public CustomEventManager(final DiceMobManager plugin) {
        this.eventExecutors = new HashMap<>();
        this.plugin = plugin;
    }

    /**
     * Broadcasts the given custom event.
     * <p>
     * Informs all registered listeners for the given
     * event and execute there event handler method.
     * Only informs all listeners from the plugin which
     * spawns the entity. Note that listeners from this plugin will
     * always be executed.
     *
     * @param event the event to broadcast
     */
    @Override
    public void callEvent(@Nonnull CustomEntityEvent event) {
        eventExecutors.getOrDefault(event.getClass(), new PriorityQueue<>()).forEach(eventExecutor -> {
            if (!event.isCancelled()) {
                final Plugin plugin = eventExecutor.getPlugin();
                if (plugin.equals(event.getPlugin()) || plugin.equals(this.plugin))
                    eventExecutor.execute(event);
            }
        });
    }

    /**
     * Registers an event listener.
     * <p>
     * Using the given plugin, to decide which plugin
     * want to register the listener.
     * Searches for {@link CustomEventHandler} annotation
     * to find all handler methods.
     * Creates an {@link EventExecutor} for each handler method and
     * puts is in to the according executor map.
     *
     * @param listener the event listener
     * @param plugin   the plugin which holds the listener
     * @throws ListenerRegistrationException when listener cant be registered.
     */
    @Override
    public void registerListener(@Nonnull Listener listener, @Nonnull Plugin plugin) {
        final Method[] methods = listener.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(CustomEventHandler.class)) {
                CustomEventHandler annotation = method.getAnnotation(CustomEventHandler.class);
                Class<?> paramClass = method.getParameterTypes()[0];
                if (method.getParameterCount() == 1 && CustomEntityEvent.class.isAssignableFrom(paramClass)) {
                    Class<? extends CustomEntityEvent> eventClass = paramClass.asSubclass(CustomEntityEvent.class);
                    method.setAccessible(true);
                    if (!eventExecutors.containsKey(eventClass)) {
                        eventExecutors.put(eventClass, new PriorityQueue<>(comparator));
                    }
                    eventExecutors.get(eventClass).add(new EventExecutor(plugin, listener, method, annotation.priority()));
                } else {
                    throw new ListenerRegistrationException("EventListeners should only have the event as a parameter.");
                }
            }
        }
    }
}
