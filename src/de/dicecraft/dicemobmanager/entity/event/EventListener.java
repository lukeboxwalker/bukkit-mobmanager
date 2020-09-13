package de.dicecraft.dicemobmanager.entity.event;

import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public interface EventListener extends Listener {

    void onEntityDeath(EntityDeathEvent event);

    void onEntitySpawn(EntitySpawnEvent event);

    void onEntityDamage(EntityDamageEvent event);
}
