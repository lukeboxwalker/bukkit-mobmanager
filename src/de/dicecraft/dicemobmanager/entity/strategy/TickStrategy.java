package de.dicecraft.dicemobmanager.entity.strategy;

import de.dicecraft.dicemobmanager.entity.event.TickEvent;
import org.bukkit.entity.Mob;

public interface TickStrategy<T extends Mob> extends Strategy<T> {

    void play(TickEvent tickEvent, T mob);
}
