package de.dicecraft.dicemobmanager.entity.strategy;

import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class StrategyType<T> {

    public static final StrategyType<EntityDeathEvent> ON_DEATH = new StrategyType<>("ON_DEATH");
    public static final StrategyType<EntityDamageEvent> ON_DAMAGE = new StrategyType<>("ON_DAMAGE");
    public static final StrategyType<EntitySpawnEvent> ON_SPAWN = new StrategyType<>("ON_SPAWN");
    public static final StrategyType<Entity> ON_TICK = new StrategyType<>("ON_TICK");
    public static final StrategyType<DeathDrop> ON_ITEM_DROP = new StrategyType<>("ON_ITEM_DROP");

    private final String name;

    public StrategyType(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
