package de.dicecraft.dicemobmanager.entity.strategy;

import de.dicecraft.dicemobmanager.entity.event.DeathEvent;

public interface DeathStrategy extends Strategy {

    void play(DeathEvent deathEvent);
}
