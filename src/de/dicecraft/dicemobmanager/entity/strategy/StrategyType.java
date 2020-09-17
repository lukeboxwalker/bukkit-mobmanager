package de.dicecraft.dicemobmanager.entity.strategy;

import de.dicecraft.dicemobmanager.entity.event.DamageEvent;
import de.dicecraft.dicemobmanager.entity.event.DeathEvent;
import de.dicecraft.dicemobmanager.entity.event.ItemDropEvent;
import de.dicecraft.dicemobmanager.entity.event.SpawnEvent;
import org.bukkit.entity.Entity;

public class StrategyType<T> {

    public static final StrategyType<DeathEvent> ON_DEATH = new StrategyType<>("ON_DEATH");
    public static final StrategyType<DamageEvent> ON_DAMAGE = new StrategyType<>("ON_DAMAGE");
    public static final StrategyType<SpawnEvent> ON_SPAWN = new StrategyType<>("ON_SPAWN");
    public static final StrategyType<Entity> ON_TICK = new StrategyType<>("ON_TICK");
    public static final StrategyType<ItemDropEvent> ON_ITEM_DROP = new StrategyType<>("ON_ITEM_DROP");

    private final String name;

    public StrategyType(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
