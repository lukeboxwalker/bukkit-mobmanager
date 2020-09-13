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

public final class CustomEventManager implements EventManager {

    private final HashMap<Class<? extends CustomEntityEvent>, Queue<EventExecutor>> eventExecutors;
    private final Comparator<EventExecutor> comparator = Comparator.comparingInt(eventExecutor -> eventExecutor.getPriority().getSlot());
    private final Plugin plugin;

    public CustomEventManager(final DiceMobManager plugin) {
        this.eventExecutors = new HashMap<>();
        this.plugin = plugin;
    }

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
