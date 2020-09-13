package de.dicecraft.dicemobmanager.entity;

public class Component<F, S> {

    private final F first;
    private final S second;

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
