package de.dicecraft.dicemobmanager.entity.strategy;

import de.dicecraft.dicemobmanager.entity.event.TickEvent;

public interface TickStrategy extends Strategy {

    void play(TickEvent tickEvent);
}
