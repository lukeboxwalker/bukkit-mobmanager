package de.dicecraft.dicemobmanager.entity.strategy;

import de.dicecraft.dicemobmanager.entity.event.DeathEvent;
import org.bukkit.entity.Mob;

public interface DeathStrategy<T extends Mob> extends Strategy<T> {

    void play(DeathEvent deathEvent, T mob);
}
