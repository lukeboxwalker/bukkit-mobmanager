package de.dicecraft.dicemobmanager.entity.strategy;

import de.dicecraft.dicemobmanager.entity.event.DamageEvent;
import org.bukkit.entity.Mob;

public interface DamageStrategy<T extends Mob> extends Strategy<T> {

    void play(DamageEvent damageEvent, T mob);
}
