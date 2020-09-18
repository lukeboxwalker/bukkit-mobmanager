package de.dicecraft.dicemobmanager.entity.strategy;

import de.dicecraft.dicemobmanager.entity.event.DamageEvent;

public interface DamageStrategy extends Strategy {

    void play(DamageEvent damageEvent);
}
