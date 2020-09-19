package de.dicecraft.dicemobmanager.configuration;

import de.dicecraft.dicemobmanager.entity.EntityManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.SlimeSplitEvent;

import java.util.Optional;

public class ConfigEventListener implements Listener {

    private final EntityManager manager;

    public ConfigEventListener(final EntityManager manager) {
        this.manager = manager;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSlimeSplit(SlimeSplitEvent event) {
        Optional<Configuration> optional = manager.getEntityConfig(event.getEntity());
        optional.ifPresent(configuration -> {
            manager.removeEntity(event.getEntity());
            event.setCancelled(!configuration.canSlimeSplit());
        });
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onExplodeDamage(ExplosionPrimeEvent event) {
       if (event.getEntity() instanceof Projectile) {
            Projectile projectile = (Projectile) event.getEntity();
            if (manager.isWatchingProjectile(projectile)) {
                Optional<Configuration> optional = manager.getEntityConfig(manager.getProjectileShooter(projectile));
                optional.ifPresent(configuration -> {
                    manager.unWatchProjectile(projectile);
                    if (!configuration.canProjectileBlockDamage()) {
                        event.getEntity().getWorld().createExplosion(event.getEntity().getLocation(), event.getRadius() , false, false);
                        event.setCancelled(true);
                    }
                });
            }
        }
    }
}

