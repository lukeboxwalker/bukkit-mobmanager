package de.dicecraft.dicemobmanager.utils;

/**
 * Entry to pair object to a priority.
 *
 * @param <T> type of entry
 * @author Walkehorst Lukas
 * @since 1.0
 */
public class PriorityEntry<T> {

    // priority of the entry
    private int priority;
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

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public T getEntry() {
        return entry;
    }

    @Override
    public String toString() {
        return "PriorityEntry{" +
                "priority=" + priority +
                ", entry=" + entry +
                '}';
    }
}
