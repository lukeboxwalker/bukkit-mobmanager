package de.dicecraft.dicemobmanager.utils;

/**
 * Pares to objects.
 *
 * @param <F> first object type
 * @param <S> second object type
 * @author Walkehorst Lukas
 * @since 1.0
 */
public class Component<F, S> {

    private final F first;
    private final S second;

    /**
     * Creates a new component containing two objects.
     *
     * @param first  the first object
     * @param second the second object
     */
    public Component(final F first, final S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }
}
