package de.dicecraft.dicemobmanager.entity;

import de.dicecraft.dicemobmanager.entity.event.CustomEntityDamageEvent;
import de.dicecraft.dicemobmanager.entity.event.CustomEntityDeathEvent;
import de.dicecraft.dicemobmanager.entity.event.CustomEntitySpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

public class EntityEventListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDeath(EntityDeathEvent event) {
        Optional<Pair<Plugin, EntityInformation>> optional = CustomEntities.getInformation(event.getEntity());
        optional.ifPresent(pair -> {
            CustomEntityDeathEvent spawnEvent = new CustomEntityDeathEvent(event, pair);
            Bukkit.getPluginManager().callEvent(spawnEvent);
            if (!spawnEvent.isCancelled()) {
                CustomEntities.removeEntity(event.getEntity(), pair.getFirst());
            }
        });
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            Optional<Pair<Plugin, EntityInformation>> optional = CustomEntities.getInformation(event.getEntity());
            optional.ifPresent(pair -> {
                CustomEntitySpawnEvent spawnEvent = new CustomEntitySpawnEvent(event, pair);
                Bukkit.getPluginManager().callEvent(spawnEvent);
            });
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntitySpawn(EntityDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            Optional<Pair<Plugin, EntityInformation>> optional = CustomEntities.getInformation(event.getEntity());
            optional.ifPresent(pair -> {
                CustomEntityDamageEvent spawnEvent = new CustomEntityDamageEvent(event, pair);
                Bukkit.getPluginManager().callEvent(spawnEvent);
            });
        }
    }
}
