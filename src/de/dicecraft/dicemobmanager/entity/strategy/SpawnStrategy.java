package de.dicecraft.dicemobmanager.entity.strategy;

import de.dicecraft.dicemobmanager.entity.event.SpawnEvent;

public interface SpawnStrategy extends Strategy {

    void play(SpawnEvent spawnEvent);
}
