package de.dicecraft.dicemobmanager.entity.strategy;

import de.dicecraft.dicemobmanager.entity.event.SpawnEvent;
import org.bukkit.entity.Mob;

public interface SpawnStrategy<T extends Mob> extends Strategy<T> {

    void play(SpawnEvent spawnEvent, T mob);
}
