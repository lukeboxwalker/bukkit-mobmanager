package de.dicecraft.dicemobmanager.entity.strategy;

@FunctionalInterface
public interface Strategy<T> {

    void play(T consumable);
}
