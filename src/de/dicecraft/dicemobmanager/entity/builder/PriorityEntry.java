package de.dicecraft.dicemobmanager.entity.builder;

/**
 * Entry to pair object to a priority.
 *
 * @param <T> type of entry
 * @author Walkehorst Lukas
 * @since 1.0
 */
public class PriorityEntry<T> {

    // priority of the entry
    private final int priority;
    private final T entry;

    /**
     * Creates a new PriorityEntry.
     *
     * @param priority of entry
     * @param entry to priorities
     */
    public PriorityEntry(final int priority, final T entry) {
        this.priority = priority;
        this.entry = entry;
    }

    public int getPriority() {
        return priority;
    }

    public T getEntry() {
        return entry;
    }
}
